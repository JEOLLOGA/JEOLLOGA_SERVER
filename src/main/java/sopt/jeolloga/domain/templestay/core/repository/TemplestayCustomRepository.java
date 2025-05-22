package sopt.jeolloga.domain.templestay.core.repository;

import sopt.jeolloga.domain.templestay.Templestay;

import java.util.List;

public interface TemplestayCustomRepository {
    List<Templestay> searchByFilters(Integer regionMask, Integer typeMask, Integer activityMask, Integer etcMask);
    long countByFilters(Integer regionMask, Integer typeMask, Integer activityMask, Integer etcMask);
}
