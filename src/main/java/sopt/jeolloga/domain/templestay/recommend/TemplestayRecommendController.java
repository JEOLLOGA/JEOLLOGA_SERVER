package sopt.jeolloga.domain.templestay.recommend;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import sopt.jeolloga.common.dto.ApiResponse;
import sopt.jeolloga.common.type.MemberType;
import sopt.jeolloga.domain.auth.jwt.CustomUserDetails;

@RestController
@RequiredArgsConstructor
public class TemplestayRecommendController {

    private final TemplestayRecommendService service;

    @GetMapping("/api/recommend/type")
    public ResponseEntity<ApiResponse<?>> recommendByType(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @RequestParam(defaultValue = "20") int limit
    ) {
        List<TemplestayPickRes> data = service.recommendForUser(userDetails.getUserId(), limit);
        return ResponseEntity.ok(ApiResponse.success(data));
    }

    @GetMapping("/api/templestay/type-random")
    public ResponseEntity<ApiResponse<?>> recommendAll(@RequestParam(defaultValue = "20") int limit) {
        List<TemplestayPickRes> results = service.recommendFlat(limit);
        return ResponseEntity.ok(ApiResponse.success(new RecommendFlatRes(results)));
    }
}