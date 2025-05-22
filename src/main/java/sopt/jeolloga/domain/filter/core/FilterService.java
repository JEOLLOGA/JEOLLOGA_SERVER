package sopt.jeolloga.domain.filter.core;

import org.springframework.stereotype.Service;

@Service
public class FilterService {
    private final FilterRepository filterRepository;

    public FilterService(FilterRepository filterRepository) {
        this.filterRepository = filterRepository;
    }
}
