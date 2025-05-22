package sopt.jeolloga.domain.templestay.core;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sopt.jeolloga.common.filter.Region;
import sopt.jeolloga.domain.filter.Filter;
import sopt.jeolloga.domain.filter.core.FilterRepository;
import sopt.jeolloga.domain.image.Image;
import sopt.jeolloga.domain.image.core.ImageRepository;
import sopt.jeolloga.domain.templestay.Templestay;
import sopt.jeolloga.domain.templestay.api.dto.TemplestayRecommendListRes;
import sopt.jeolloga.domain.templestay.api.dto.TemplestayRecommendRes;
import sopt.jeolloga.domain.templestay.core.repository.TemplestayRepository;

import java.util.List;
import java.util.Optional;

@Service
public class TemplestayService {
    private final TemplestayRepository templestayRepository;
    private final FilterRepository filterRepository;
    private final ImageRepository imageRepository;

    public TemplestayService(TemplestayRepository templestayRepository, FilterRepository filterRepository, ImageRepository imageRepository) {
        this.templestayRepository = templestayRepository;
        this.filterRepository = filterRepository;
        this.imageRepository = imageRepository;
    }

    @Transactional(readOnly = true)
    public TemplestayRecommendListRes getRecommendTemplestays() {
        List<Long> templestayIds = List.of(2002L, 2003L, 2004L);

        List<TemplestayRecommendRes> results = templestayIds.stream()
                .map(id -> {
                    // 기존 getRecommendTemplestay 로직
                    Templestay templestay = templestayRepository.findById(id)
                            .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 템플스테이 id입니다: " + id));

                    Filter filter = filterRepository.findFirstByTemplestayId(id)
                            .orElseThrow(() -> new IllegalArgumentException("필터 정보가 없습니다: " + id));

                    int regionMask = filter.getRegion();
                    List<String> regionNames = Region.fromMask(regionMask).stream()
                            .map(Region::getLabel)
                            .toList();
                    String region = String.join(", ", regionNames);

                    Optional<Image> optionalImage = imageRepository.findFirstByTemplestayIdOrderByIdAsc(id);
                    String imgUrl = optionalImage.map(Image::getImgUrl).orElse(null);

                    return new TemplestayRecommendRes(
                            templestay.getId(),
                            imgUrl,
                            region,
                            templestay.getTempleName()
                    );
                })
                .toList();

        return new TemplestayRecommendListRes(results);
    }
}
