package sopt.jeolloga.domain.templestay.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import sopt.jeolloga.common.filter.Region;
import sopt.jeolloga.domain.filter.Filter;
import sopt.jeolloga.domain.filter.core.FilterRepository;
import sopt.jeolloga.domain.image.Image;
import sopt.jeolloga.domain.image.core.ImageRepository;
import sopt.jeolloga.domain.templestay.Templestay;
import sopt.jeolloga.domain.templestay.api.dto.TemplestayRecommendRes;
import sopt.jeolloga.domain.templestay.core.repository.TemplestayRepository;
import sopt.jeolloga.domain.wishlist.core.WishlistRepository;
import sopt.jeolloga.exception.BusinessErrorCode;
import sopt.jeolloga.exception.BusinessException;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class TemplestayRecommendMapper {

    private final TemplestayRepository templestayRepository;
    private final FilterRepository filterRepository;
    private final ImageRepository imageRepository;
    private final WishlistRepository wishlistRepository;

    public Optional<TemplestayRecommendRes> toRecommendRes(Long id, int rank, Long userId) {
        return templestayRepository.findById(id)
                .filter(t -> t.getOrganizedName() != null)
                .flatMap(templestay -> {
                    Filter filter = filterRepository.findFirstByTemplestay_Id(id)
                            .orElseThrow(() -> new BusinessException(BusinessErrorCode.NOT_FOUND_FILTER));

                    String region = Region.fromMask(filter.getRegion()).stream()
                            .map(Region::getLabel)
                            .reduce((a, b) -> a + ", " + b)
                            .orElse("");

                    String imgUrl = imageRepository.findFirstByTemplestay_IdOrderByIdAsc(id)
                            .map(Image::getImgUrl)
                            .orElse(null);

                    boolean wish = userId != null && wishlistRepository.existsByMember_IdAndTemplestay_Id(userId, id);

                    return Optional.of(new TemplestayRecommendRes(
                            templestay.getId(),
                            rank,
                            templestay.getOrganizedName(),
                            imgUrl,
                            region,
                            templestay.getTempleName(),
                            wish
                    ));
                });
    }
}
