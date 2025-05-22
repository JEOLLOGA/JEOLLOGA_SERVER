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
import sopt.jeolloga.exception.BusinessErrorCode;
import sopt.jeolloga.exception.BusinessException;

@Component
@RequiredArgsConstructor
public class TemplestayRecommendMapper {

    private final TemplestayRepository templestayRepository;
    private final FilterRepository filterRepository;
    private final ImageRepository imageRepository;

    public TemplestayRecommendRes toRecommendRes(Long id) {
        Templestay templestay = templestayRepository.findById(id)
                .orElseThrow(() -> new BusinessException(BusinessErrorCode.NOT_FOUND_TEMPLESTAY));

        Filter filter = filterRepository.findFirstByTemplestayId(id)
                .orElseThrow(() -> new BusinessException(BusinessErrorCode.NOT_FOUND_FILTER));

        String region = Region.fromMask(filter.getRegion()).stream()
                .map(Region::getLabel)
                .toList()
                .stream()
                .reduce((a, b) -> a + ", " + b)
                .orElse("");

        String imgUrl = imageRepository.findFirstByTemplestayIdOrderByIdAsc(id)
                .map(Image::getImgUrl)
                .orElse(null);

        return new TemplestayRecommendRes(
                templestay.getId(),
                imgUrl,
                region,
                templestay.getTempleName()
        );
    }
}