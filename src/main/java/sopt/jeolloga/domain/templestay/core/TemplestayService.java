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
import sopt.jeolloga.domain.templestay.mapper.TemplestayRecommendMapper;
import sopt.jeolloga.exception.BusinessErrorCode;
import sopt.jeolloga.exception.BusinessException;
import sopt.jeolloga.exception.ErrorCode;

import java.util.List;
import java.util.Optional;

@Service
public class TemplestayService {
    // 이번주 인기 템플스테이 아이디
    private static final List<Long> RECOMMEND_TEMPLATESTAY_IDS = List.of(2002L, 2003L, 2004L);

    private final TemplestayRepository templestayRepository;
    private final FilterRepository filterRepository;
    private final ImageRepository imageRepository;
    private final TemplestayRecommendMapper templestayRecommendMapper;

    public TemplestayService(TemplestayRepository templestayRepository, FilterRepository filterRepository, ImageRepository imageRepository, TemplestayRecommendMapper templestayRecommendMapper) {
        this.templestayRepository = templestayRepository;
        this.filterRepository = filterRepository;
        this.imageRepository = imageRepository;
        this.templestayRecommendMapper = templestayRecommendMapper;
    }

    @Transactional(readOnly = true)
    public TemplestayRecommendListRes getRecommendTemplestays() {
        List<TemplestayRecommendRes> results = RECOMMEND_TEMPLATESTAY_IDS.stream()
                .map(templestayRecommendMapper::toRecommendRes)
                .flatMap(Optional::stream)
                .toList();

        return new TemplestayRecommendListRes(results);
    }
}
