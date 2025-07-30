package sopt.jeolloga.domain.templestay.api;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import sopt.jeolloga.domain.templestay.core.ReviewApiService;

@RestController
@RequiredArgsConstructor
public class ReviewApiController {
    private final ReviewApiService reviewApiService;
}
