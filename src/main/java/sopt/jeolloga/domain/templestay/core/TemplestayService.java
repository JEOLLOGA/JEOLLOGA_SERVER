package sopt.jeolloga.domain.templestay.core;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sopt.jeolloga.domain.filter.core.FilterRepository;
import sopt.jeolloga.domain.image.core.ImageRepository;
import sopt.jeolloga.domain.templestay.Templestay;
import sopt.jeolloga.domain.templestay.api.dto.TemplestayDetailsRes;
import sopt.jeolloga.domain.templestay.api.dto.TemplestayRecommendListRes;
import sopt.jeolloga.domain.templestay.api.dto.TemplestayRecommendRes;
import sopt.jeolloga.domain.templestay.core.repository.TemplestayRepository;
import sopt.jeolloga.domain.templestay.mapper.TemplestayDetailsMapper;
import sopt.jeolloga.domain.templestay.mapper.TemplestayRecommendMapper;
import sopt.jeolloga.exception.BusinessErrorCode;
import sopt.jeolloga.exception.BusinessException;

import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

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
        List<TemplestayRecommendRes> results = IntStream.range(0, RECOMMEND_TEMPLATESTAY_IDS.size())
                .mapToObj(i -> {
                    Long id = RECOMMEND_TEMPLATESTAY_IDS.get(i);
                    int rank = i + 1;
                    return templestayRecommendMapper.toRecommendRes(id, rank);
                })
                .flatMap(Optional::stream)
                .toList();

        return new TemplestayRecommendListRes(results);
    }

    @Transactional(readOnly = true)
    public TemplestayDetailsRes getDetailsTemplestay(Long id) {
        return templestayRepository.findDetailsById(id)
                .flatMap(TemplestayDetailsMapper::validateLatLon)
                .orElseThrow(() -> new BusinessException(BusinessErrorCode.NOT_FOUND_TEMPLESTAY));
    }

    @Transactional
    public void updateView(Long id) {
        Templestay templestay = templestayRepository.findById(id)
                .orElseThrow(() -> new BusinessException(BusinessErrorCode.NOT_FOUND_TEMPLESTAY));
        templestay.updateView();
    }
}
