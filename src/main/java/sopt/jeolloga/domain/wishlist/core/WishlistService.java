package sopt.jeolloga.domain.wishlist.core;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sopt.jeolloga.domain.member.Member;
import sopt.jeolloga.domain.member.core.MemberRepository;
import sopt.jeolloga.domain.templestay.Templestay;
import sopt.jeolloga.domain.templestay.core.repository.TemplestayRepository;
import sopt.jeolloga.domain.wishlist.Wishlist;
import sopt.jeolloga.domain.wishlist.api.dto.WishlistPageRes;
import sopt.jeolloga.domain.wishlist.api.dto.WishlistRes;
import sopt.jeolloga.domain.wishlist.core.querydsl.WishlistCustomRepository;
import sopt.jeolloga.exception.BusinessErrorCode;
import sopt.jeolloga.exception.BusinessException;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class WishlistService {

    private final MemberRepository memberRepository;
    private final TemplestayRepository templestayRepository;
    private final WishlistRepository wishlistRepository;
    private final WishlistCustomRepository wishlistCustomRepository;

    @Transactional
    public void updateWishlist(Long userId, Long templestayId) {
        Member member = memberRepository.findById(userId)
                .orElseThrow(() -> new BusinessException(BusinessErrorCode.NOT_FOUND_USER));

        Templestay templestay = templestayRepository.findById(templestayId)
                .orElseThrow(() -> new BusinessException(BusinessErrorCode.NOT_FOUND_TEMPLESTAY));

        Optional<Wishlist> existing = wishlistRepository.findByMemberAndTemplestay(member, templestay);

        if (existing.isPresent()) {
            wishlistRepository.delete(existing.get());
        } else {
            Wishlist wishlist = Wishlist.builder()
                    .member(member)
                    .templestay(templestay)
                    .build();
            wishlistRepository.save(wishlist);
        }
    }

    @Transactional
    public void deleteWishlist(Long userId, Long templestayId) {
        Member member = memberRepository.findById(userId)
                .orElseThrow(() -> new BusinessException(BusinessErrorCode.NOT_FOUND_USER));

        Templestay templestay = templestayRepository.findById(templestayId)
                .orElseThrow(() -> new BusinessException(BusinessErrorCode.NOT_FOUND_TEMPLESTAY));

        Wishlist wishlist = wishlistRepository.findByMemberAndTemplestay(member, templestay)
                .orElseThrow(() -> new BusinessException(BusinessErrorCode.NOT_FOUND_WISHLIST));

        wishlistRepository.delete(wishlist);
    }

    public WishlistPageRes getWishlist(Long userId, int page, int size) {
        int offset = (page - 1) * size;

        List<WishlistRes> content = wishlistCustomRepository.findWishlistContent(userId, offset, size);
        long total = wishlistCustomRepository.countWishlist(userId);

        return WishlistPageRes.of(content, page, size, total);
    }
}