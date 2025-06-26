package sopt.jeolloga.domain.search.core;

import org.springframework.stereotype.Service;

@Service
public class SearchService {
    private final SearchRepository searchRepository;

    public SearchService(SearchRepository searchRepository) {
        this.searchRepository = searchRepository;
    }
}
