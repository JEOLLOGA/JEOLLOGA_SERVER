package sopt.jeolloga.domain.templestay.core;

import com.querydsl.core.Tuple;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sopt.jeolloga.common.filter.*;
import sopt.jeolloga.domain.auth.jwt.CustomUserDetails;
import sopt.jeolloga.domain.image.core.ImageRepository;
import sopt.jeolloga.domain.member.core.MemberRepository;
import sopt.jeolloga.domain.search.Search;
import sopt.jeolloga.domain.search.core.SearchRepository;
import sopt.jeolloga.domain.templestay.Templestay;
import sopt.jeolloga.domain.templestay.api.dto.*;
import sopt.jeolloga.domain.templestay.core.repository.TemplestayRepository;
import sopt.jeolloga.domain.templestay.mapper.TemplestayDetailsMapper;
import sopt.jeolloga.domain.templestay.mapper.TemplestayRecommendMapper;
import sopt.jeolloga.domain.wishlist.core.WishlistRepository;
import sopt.jeolloga.exception.BusinessErrorCode;
import sopt.jeolloga.exception.BusinessException;
import sopt.jeolloga.util.FilterMaskUtil;
import sopt.jeolloga.domain.image.Image;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static sopt.jeolloga.util.FilterMaskUtil.decodeMask;

@Service
@RequiredArgsConstructor
public class TemplestayService {

    private static final List<Long> RECOMMEND_TEMPLATESTAY_IDS = List.of(34L, 35L, 36L);

    private final TemplestayRepository templestayRepository;
    private final ImageRepository imageRepository;
    private final TemplestayRecommendMapper templestayRecommendMapper;
    private final SearchRepository searchRepository;
    private final WishlistRepository wishlistRepository;
    private final MemberRepository memberRepository;

    @Transactional(readOnly = true)
    public TemplestayRecommendListRes getRecommendTemplestays(Long userId) {
        List<TemplestayRecommendRes> results = IntStream.range(0, RECOMMEND_TEMPLATESTAY_IDS.size())
                .mapToObj(i -> templestayRecommendMapper.toRecommendRes(RECOMMEND_TEMPLATESTAY_IDS.get(i), i + 1, userId))
                .flatMap(Optional::stream)
                .toList();

        return new TemplestayRecommendListRes(results);
    }

    @Transactional(readOnly = true)
    public TemplestayDetailsRes getDetailsTemplestay(Long id, Long userId) {
        Tuple row = templestayRepository.findDetailsWithPriceById(id)
                .orElseThrow(() -> new BusinessException(BusinessErrorCode.NOT_FOUND_TEMPLESTAY));

        Templestay templestay = row.get(0, Templestay.class);
        Integer price = row.get(1, Integer.class);

        boolean wish = userId != null && wishlistRepository.existsByMember_IdAndTemplestay_Id(userId, id);

        return TemplestayDetailsMapper.map(templestay, price, wish)
                .orElseThrow(() -> new BusinessException(BusinessErrorCode.NOT_FOUND_TEMPLESTAY));
    }

    @Transactional
    public void updateView(Long id) {
        Templestay templestay = templestayRepository.findById(id)
                .orElseThrow(() -> new BusinessException(BusinessErrorCode.NOT_FOUND_TEMPLESTAY));
        templestay.updateView();
    }

    @Transactional
    public TemplestayPageRes getTemplestays(
            Set<Region> region,
            Set<Type> type,
            Set<Activity> activity,
            Set<EtcOption> etc,
            Integer min,
            Integer max,
            String sort,
            String search,
            CustomUserDetails user,
            int page,
            int size
    ) {
        int regionMask = FilterMaskUtil.combineMasks(region);
        int typeMask = FilterMaskUtil.combineMasks(type);
        int activityMask = FilterMaskUtil.combineMasks(activity);
        int etcMask = FilterMaskUtil.combineMasks(etc);

        if (user != null && search != null && !search.isBlank()) {
            memberRepository.findById(user.getUserId()).ifPresent(member ->
                    searchRepository.save(new Search(member, search))
            );
        }

        int offset = (Math.max(page, 1) - 1) * size;

        boolean isEmptyParam = (region == null || region.isEmpty())
                && (type == null || type.isEmpty())
                && (activity == null || activity.isEmpty())
                && (etc == null || etc.isEmpty())
                && min == null
                && max == null
                && (search == null || search.isBlank());

        String effectiveSort = (sort == null || sort.isBlank()) ? "recommend" : sort;

        List<Object[]> rows = templestayRepository.fetchFilteredTemplestays(
                regionMask, typeMask, activityMask, etcMask,
                min, max, effectiveSort, search, offset, size
        );

        if (effectiveSort.equalsIgnoreCase("recommend") && isEmptyParam) {
            Collections.shuffle(rows);
        }

        long totalCount = templestayRepository.countFilteredTemplestays(
                regionMask, typeMask, activityMask, etcMask,
                min, max, search
        );

        List<TemplestayFilterDto> result = rows.stream()
                .map(row -> new TemplestayFilterDto(
                        ((Number) row[0]).longValue(),
                        (String) row[1],
                        (String) row[2],
                        row[3] != null ? ((Number) row[3]).intValue() : 0,
                        row[4] != null ? ((Number) row[4]).intValue() : 0,
                        row[5] != null ? ((Number) row[5]).intValue() : 0
                ))
                .toList();

        List<Long> templestayIds = result.stream()
                .map(TemplestayFilterDto::templestayId)
                .toList();

        Map<Long, String> imgUrlMap = imageRepository.findFirstImagesByTemplestayIds(templestayIds).stream()
                .collect(Collectors.toMap(
                        image -> image.getTemplestay().getId(),
                        Image::getImgUrl
                ));

        List<Long> wishIds = (user != null)
                ? wishlistRepository.findTemplestayIdsByMemberId(user.getUserId())
                : List.of();

        List<TemplestayRes> responses = result.stream()
                .map(dto -> new TemplestayRes(
                        dto.templestayId(),
                        dto.templeName(),
                        dto.templestayName(),
                        decodeMask(dto.region(), Region.class),
                        decodeMask(dto.type(), Type.class),
                        imgUrlMap.getOrDefault(dto.templestayId(), null),
                        wishIds.contains(dto.templestayId())
                ))
                .toList();

        return TemplestayPageRes.of(responses, page, size, totalCount);
    }
}