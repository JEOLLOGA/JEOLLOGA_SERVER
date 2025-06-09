package sopt.jeolloga.domain.auth.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sopt.jeolloga.common.dto.ApiResponse;
import sopt.jeolloga.domain.auth.dto.LoginCommand;
import sopt.jeolloga.domain.auth.dto.LoginResult;
import sopt.jeolloga.domain.auth.service.LoginService;
import sopt.jeolloga.domain.auth.service.LogoutService;
import sopt.jeolloga.domain.auth.service.ReissueService;

@RestController
@RequestMapping("/api/auth/kakao")
@RequiredArgsConstructor
public class OAuthController {
    private final LoginService loginService;
    private final LogoutService logoutService;
    private final ReissueService reissueService;

    @GetMapping("/login")
    public ResponseEntity<ApiResponse<?>> login(@RequestParam String code) {
        LoginResult result = loginService.login(new LoginCommand(code));
        return ResponseEntity.ok(ApiResponse.success(result));
    }

    @PostMapping("/logout")
    public ResponseEntity<ApiResponse<?>> logout(@RequestHeader("Authorization") String bearerToken) {
        String token = bearerToken.replace("Bearer ", "");
        logoutService.logout(token);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/reissue")
    public ResponseEntity<ApiResponse<?>> reissue(@RequestHeader("userId") Long userId,
                                               @RequestHeader("refreshToken") String refreshToken) {
        return ResponseEntity.ok(ApiResponse.success(reissueService.reissue(userId, refreshToken)));
    }
}
