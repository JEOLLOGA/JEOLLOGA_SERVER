package sopt.jeolloga.filter.api;

import org.springframework.web.bind.annotation.RestController;
import sopt.jeolloga.filter.core.FilterService;

@RestController
public class FilterController {
    private final FilterService filterService;

    public FilterController(FilterService filterService) {
        this.filterService = filterService;
    }
}
