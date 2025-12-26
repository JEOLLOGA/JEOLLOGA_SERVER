package sopt.jeolloga.domain.templestay.recommend;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sopt.jeolloga.common.dto.ApiResponse;
import sopt.jeolloga.common.type.MemberType;

@RestController
@RequestMapping("/v3")
@RequiredArgsConstructor
public class TemplestayRecommendController {

    private final TemplestayRecommendService service;

    @GetMapping("/user/recommend/type")
    public ResponseEntity<ApiResponse<?>> recommendByType(
            @RequestParam MemberType type,
            @RequestParam(defaultValue = "20") int limit
    ) {
        List<TemplestayPickRes> data = service.recommendByType(type, limit);
        return ResponseEntity.ok(ApiResponse.success(data));
    }

    // Controller
    @GetMapping("/api/recommend/type")
    public ResponseEntity<ApiResponse<?>> recommendAll(@RequestParam(defaultValue = "20") int limit) {
        List<TemplestayPickRes> results = service.recommendFlat(limit);
        return ResponseEntity.ok(ApiResponse.success(new RecommendFlatRes(results)));
    }
}