package sopt.jeolloga.domain.member.api;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sopt.jeolloga.common.dto.ApiResponse;
import sopt.jeolloga.common.type.MemberType;
import sopt.jeolloga.domain.member.api.dto.req.TestResultReq;
import sopt.jeolloga.domain.member.api.dto.res.TypeResultRes;
import sopt.jeolloga.domain.member.core.RecommendService;

@RestController
@RequestMapping("/api/templestay")
public class RecommendController {

    private final RecommendService recommendService;

    public RecommendController(RecommendService recommendService) {
        this.recommendService = recommendService;
    }

    @GetMapping("/type")
    public ResponseEntity<ApiResponse<?>> getByType(
            @RequestParam MemberType type
    ) {
        TypeResultRes res = recommendService.recommendByType(type);
        return ResponseEntity.ok(ApiResponse.success(res));
    }

    @PostMapping(value = "/type", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse<?>> getByTestResult(@RequestBody TestResultReq req) {
        TypeResultRes res = recommendService.recommendByTestResult(req.result());
        return ResponseEntity.ok(ApiResponse.success(res));
    }
}

