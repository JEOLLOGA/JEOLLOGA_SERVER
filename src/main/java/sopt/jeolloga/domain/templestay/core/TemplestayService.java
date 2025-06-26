package sopt.jeolloga.domain.templestay.core;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sopt.jeolloga.common.filter.*;
import sopt.jeolloga.domain.auth.jwt.CustomUserDetails;
import sopt.jeolloga.domain.filter.core.FilterRepository;
import sopt.jeolloga.domain.image.core.ImageRepository;
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

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static jdk.internal.org.jline.utils.Colors.s;

@Service
public class TemplestayService {
    // 이번주 인기 템플스테이 아이디
    private static final List<Long> RECOMMEND_TEMPLATESTAY_IDS = List.of(1L, 2L, 3L);

    private final TemplestayRepository templestayRepository;
    private final FilterRepository filterRepository;
    private final ImageRepository imageRepository;
    private final TemplestayRecommendMapper templestayRecommendMapper;
    private final SearchRepository searchRepository;
    private final WishlistRepository wishlistRepository;


    public TemplestayService(TemplestayRepository templestayRepository, FilterRepository filterRepository, ImageRepository imageRepository, TemplestayRecommendMapper templestayRecommendMapper, SearchRepository searchRepository, WishlistRepository wishlistRepository) {
        this.templestayRepository = templestayRepository;
        this.filterRepository = filterRepository;
        this.imageRepository = imageRepository;
        this.templestayRecommendMapper = templestayRecommendMapper;
        this.searchRepository = searchRepository;
        this.wishlistRepository = wishlistRepository;
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

    @Transactional(readOnly = true)
    public TemplestayListRes getTemplestays(
            String region,
            String type,
            String activity,
            String etc,
            String sort,
            String search,
            CustomUserDetails user
    ) {
        int regionMask = toMask(region, Region.class);
        int typeMask = toMask(type, Type.class);
        int activityMask = toMask(activity, Activity.class);
        int etcMask = toMask(etc, EtcOption.class);

        if (user != null && search != null && !search.isBlank()) {
            searchRepository.save(SearchRepository.of(user.getUserId(), search));
        }

        List<Templestay> result = templestayRepository.searchByFiltersAndSearch(
                regionMask, typeMask, activityMask, etcMask, sort, search
        );

        List<Long> wishIds = (user != null)
                ? wishlistRepository.findTemplestayIdsByMemberId(user.getUserId())
                : List.of();

        List<TemplestayRes> responses = result.stream()
                .map(t -> new TemplestayRes(
                        t.getId(),
                        t.getTempleName(),
                        t.getOrganizedName(),
                        decodeMask(t.getFilter().getRegion, Region.class),
                        decodeMask(t.getFilter().getType, Type.class),
                        wishIds.contains(t.getId())
                )).toList();

        return new TemplestayListRes(responses);
    }

    private <E extends Enum<E> & BitMask> int toMask(String raw, Class<E> enumClass) {
        if (raw == null || raw.isBlank()) {
            return 0;
        }

        return Arrays.stream(raw.split(","))
                .map(String::trim)
                .map(s -> Enum.valueOf(enumClass, s))
                .mapToInt(BitMask::getMask)
                .reduce(0, (a, b) -> a | b);
    }

    private <E extends Enum<E> & BitMask> String decodeMask(int mask, Class<E> enumClass) {
        return Arrays.stream(enumClass.getEnumConstants())
                .filter(e -> (e.getMask() & mask) != 0)
                .map(BitMask::getLabel)
                .collect(Collectors.joining(","));
    }
}
