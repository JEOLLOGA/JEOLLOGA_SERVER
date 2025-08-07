package sopt.jeolloga.domain.templestay.api;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import sopt.jeolloga.common.dto.ApiResponse;
import sopt.jeolloga.common.filter.Activity;
import sopt.jeolloga.common.filter.EtcOption;
import sopt.jeolloga.common.filter.Region;
import sopt.jeolloga.common.filter.Type;
import sopt.jeolloga.domain.auth.jwt.CustomUserDetails;
import sopt.jeolloga.domain.auth.jwt.JwtCookieProvider;
import sopt.jeolloga.domain.auth.jwt.JwtTokenGenerator;
import sopt.jeolloga.domain.templestay.api.dto.res.TemplestayDetailsRes;
import sopt.jeolloga.domain.templestay.api.dto.res.TemplestayRecommendListRes;
import sopt.jeolloga.domain.templestay.core.TemplestayService;
import sopt.jeolloga.domain.templestay.api.dto.res.TemplestayPageRes;

import java.util.Set;

@RestController
@RequestMapping("/api/templestay")
public class TemplestayController {
    private final TemplestayService templestayService;
    private final JwtCookieProvider jwtCookieProvider;

    private final JwtTokenGenerator jwtTokenGenerator;

    public TemplestayController(TemplestayService templestayService, JwtCookieProvider jwtCookieProvider, JwtTokenGenerator jwtTokenGenerator) {
        this.templestayService = templestayService;
        this.jwtCookieProvider = jwtCookieProvider;
        this.jwtTokenGenerator = jwtTokenGenerator;
    }

    @GetMapping("/recommendation")
    public ResponseEntity<ApiResponse<?>> getRecommendTemplestay(HttpServletRequest request) {
        Long userId = null;
        try {
            String token = jwtCookieProvider.extractAccessToken(request);
            if (token != null && !token.isBlank()) {
                userId = jwtTokenGenerator.extractUserId(token);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        TemplestayRecommendListRes response = templestayService.getRecommendTemplestays(userId);
        return ResponseEntity.ok(ApiResponse.success(response));
    }


    @GetMapping("/details/{id}")
    public ResponseEntity<ApiResponse<?>> getDetailsTemplestay(
            HttpServletRequest request,
            @PathVariable Long id
    ) {
        Long userId = null;
        try {
            String token = jwtCookieProvider.extractAccessToken(request);
            if (token != null && !token.isBlank()) {
                userId = jwtTokenGenerator.extractUserId(token);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        TemplestayDetailsRes templestayDetailsRes = templestayService.getDetailsTemplestay(id, userId);
        return ResponseEntity.ok(ApiResponse.success(templestayDetailsRes));
    }


    @PostMapping("/view/{id}")
    public ResponseEntity<ApiResponse<?>> updateView(@PathVariable Long id) {
        templestayService.updateView(id);
        return ResponseEntity.ok(ApiResponse.success(null));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<?>> getTemplestays(
            @RequestParam(required = false) Set<Region> region,
            @RequestParam(required = false) Set<Type> type,
            @RequestParam(required = false) Set<Activity> activity,
            @RequestParam(required = false) Set<EtcOption> etc,
            @RequestParam(required = false) Integer min,
            @RequestParam(required = false) Integer max,
            @RequestParam(required = false) String sort,
            @RequestParam(required = false) String search,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "5") int size,
            HttpServletRequest request
    ) {
        CustomUserDetails user = null;
        try {
            String token = jwtCookieProvider.extractAccessToken(request);

            if (token != null && !token.isBlank()) {
                Long userId = jwtTokenGenerator.extractUserId(token);
                user = new CustomUserDetails(userId);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        TemplestayPageRes result = templestayService.getTemplestays(
                region, type, activity, etc, min, max, sort, search, user, page, size
        );
        return ResponseEntity.ok(ApiResponse.success(result));
    }
}
