package sopt.jeolloga.domain.review;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import sopt.jeolloga.common.dto.ApiResponse;

@RestController
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping("/api/templestay/reviews/fetch-and-save")
    public ResponseEntity<ApiResponse<?>> saveTempleReviews(@RequestParam(defaultValue = "0") int page) {
        return ResponseEntity.ok(reviewService.saveTempleReviews(page));
    }
}
