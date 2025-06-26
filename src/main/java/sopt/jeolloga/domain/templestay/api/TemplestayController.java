package sopt.jeolloga.domain.templestay.api;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import sopt.jeolloga.common.dto.ApiResponse;
import sopt.jeolloga.common.filter.Activity;
import sopt.jeolloga.common.filter.EtcOption;
import sopt.jeolloga.common.filter.Region;
import sopt.jeolloga.common.filter.Type;
import sopt.jeolloga.domain.auth.jwt.CustomUserDetails;
import sopt.jeolloga.domain.templestay.api.dto.TemplestayDetailsRes;
import sopt.jeolloga.domain.templestay.api.dto.TemplestayListRes;
import sopt.jeolloga.domain.templestay.api.dto.TemplestayRecommendListRes;
import sopt.jeolloga.domain.templestay.api.dto.TemplestayRecommendRes;
import sopt.jeolloga.domain.templestay.core.TemplestayService;

import java.util.Set;

@RestController
@RequestMapping("/api/templestay")
public class TemplestayController {
    private final TemplestayService templestayService;

    public TemplestayController(TemplestayService templestayService) {
        this.templestayService = templestayService;
    }

    @GetMapping("/recommendation")
    public ResponseEntity<ApiResponse<?>> getRecommendTemplestay() {
        TemplestayRecommendListRes templestayRecommendRes = templestayService.getRecommendTemplestays();
        return ResponseEntity.ok(ApiResponse.success(templestayRecommendRes));
    }

    @GetMapping("/details/{id}")
    public ResponseEntity<ApiResponse<?>> getDetailsTemplestay(@PathVariable Long id) {
        TemplestayDetailsRes templestayDetailsRes = templestayService.getDetailsTemplestay(id);
        return ResponseEntity.ok(ApiResponse.success(templestayDetailsRes));
    }

    @PostMapping("/view/{id}")
    public ResponseEntity<ApiResponse<?>> updateView(@PathVariable Long id) {
        templestayService.updateView(id);
        return ResponseEntity.ok(ApiResponse.success(null));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<?>> getTemplestays(
            @AuthenticationPrincipal CustomUserDetails user,
            @RequestParam(required = false) Set<Region> region,
            @RequestParam(required = false) Set<Type> type,
            @RequestParam(required = false) Set<Activity> activity,
            @RequestParam(required = false) Set<EtcOption> etc,
            @RequestParam(required = false) Integer min,
            @RequestParam(required = false) Integer max,
            @RequestParam(required = false) String sort,
            @RequestParam(required = false) String search
    ) {
        TemplestayListRes templestayListRes = templestayService.getTemplestays(
                region, type, activity, etc, min, max, sort, search, user
        );
        return ResponseEntity.ok(ApiResponse.success(templestayListRes));
    }
}
