package sopt.jeolloga.domain.templestay.core.repository.querydsl;

import sopt.jeolloga.domain.templestay.Templestay;
import sopt.jeolloga.domain.templestay.api.dto.TemplestayDetailsRes;

import java.util.List;
import java.util.Optional;

public interface TemplestayCustomRepository {
    List<Templestay> searchByFilters(Integer regionMask, Integer typeMask, Integer activityMask, Integer etcMask);
    long countByFilters(Integer regionMask, Integer typeMask, Integer activityMask, Integer etcMask);
    Optional<TemplestayDetailsRes> findDetailsById(Long id);

    List<Object[]> fetchFilteredTemplestays(
            Integer regionMask, Integer typeMask, Integer activityMask, Integer etcMask,
            Integer minPrice, Integer maxPrice,
            String sort, String search
    );
}