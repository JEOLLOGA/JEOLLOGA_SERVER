package sopt.jeolloga.domain.search.api.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sopt.jeolloga.common.dto.ApiResponse;
import sopt.jeolloga.domain.auth.jwt.JwtCookieProvider;
import sopt.jeolloga.domain.auth.jwt.JwtTokenGenerator;
import sopt.jeolloga.domain.search.api.dto.res.SearchListRes;
import sopt.jeolloga.domain.search.core.SearchService;

@RestController
@RequestMapping("/user")
public class SearchController {
    private final SearchService searchService;
    private final JwtCookieProvider jwtCookieProvider;
    private final JwtTokenGenerator jwtTokenGenerator;

    public SearchController(SearchService searchService, JwtCookieProvider jwtCookieProvider, JwtTokenGenerator jwtTokenGenerator) {
        this.searchService = searchService;
        this.jwtCookieProvider = jwtCookieProvider;
        this.jwtTokenGenerator = jwtTokenGenerator;
    }

    @GetMapping("/search-list")
    public ResponseEntity<ApiResponse<?>> getSearch(
            HttpServletRequest request
    ) {
        String accessToken = jwtCookieProvider.extractAccessToken(request);
        Long userId = jwtTokenGenerator.extractUserId(accessToken);

        SearchListRes response = searchService.getSearch(userId);

        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @DeleteMapping("/search/{id}")
    public ResponseEntity<ApiResponse<?>> deleteSearch(
            HttpServletRequest request,
            @Valid @PathVariable Long id) {
        String accessToken = jwtCookieProvider.extractAccessToken(request);
        Long userId = jwtTokenGenerator.extractUserId(accessToken);

        searchService.deleteSearch(userId, id);
        return ResponseEntity.ok(ApiResponse.success(null));
    }

    @DeleteMapping("/search")
    public ResponseEntity<ApiResponse<?>> deleteAllSearch(
            HttpServletRequest request
    ) {
        String accessToken = jwtCookieProvider.extractAccessToken(request);
        Long userId = jwtTokenGenerator.extractUserId(accessToken);

        searchService.deleteAllSearch(userId);
        return ResponseEntity.ok(ApiResponse.success(null));
    }
}
