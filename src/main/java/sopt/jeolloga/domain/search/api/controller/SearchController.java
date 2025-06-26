package sopt.jeolloga.domain.search.api.controller;

import org.springframework.web.bind.annotation.RestController;
import sopt.jeolloga.domain.search.core.SearchService;

@RestController
public class SearchController {
    private final SearchService searchService;

    public SearchController(SearchService searchService) {
        this.searchService = searchService;
    }
}
