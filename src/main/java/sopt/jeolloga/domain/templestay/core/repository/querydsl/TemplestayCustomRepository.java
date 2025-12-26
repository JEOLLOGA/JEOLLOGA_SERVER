package sopt.jeolloga.domain.templestay.core.repository.querydsl;

import com.querydsl.core.Tuple;
import java.util.List;
import java.util.Optional;
import sopt.jeolloga.domain.templestay.Templestay;
import sopt.jeolloga.domain.templestay.api.dto.res.TemplestayDetailsRes;
import sopt.jeolloga.domain.templestay.recommend.TemplestayPickRes;

public interface TemplestayCustomRepository {

    List<Templestay> searchByFilters(Integer regionMask, Integer typeMask, Integer activityMask, Integer etcMask);

    long countByFilters(Integer regionMask, Integer typeMask, Integer activityMask, Integer etcMask);

    Optional<TemplestayDetailsRes> findDetailsById(Long id);

    List<Object[]> fetchFilteredTemplestays(
            Integer regionMask,
            Integer typeMask,
            Integer activityMask,
            Integer etcMask,
            Integer minPrice,
            Integer maxPrice,
            String sort,
            String search,
            int offset,
            int limit
    );

    Optional<Tuple> findDetailsWithPriceById(Long id);

    long countFilteredTemplestays(
            Integer regionMask,
            Integer typeMask,
            Integer activityMask,
            Integer etcMask,
            Integer minPrice,
            Integer maxPrice,
            String search
    );

    List<TemplestayPickRes> findTopByMasks(
            int typeMask,
            int activityMask,
            Integer minPrice,
            Integer maxPrice,
            int limit
    );

    List<TemplestayPickRes> findRandomByType(int typeMask, int limit);
}