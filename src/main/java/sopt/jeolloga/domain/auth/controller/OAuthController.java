package sopt.jeolloga.domain.auth.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sopt.jeolloga.common.dto.ApiResponse;
import sopt.jeolloga.domain.auth.dto.LoginCommand;
import sopt.jeolloga.domain.auth.dto.LoginResult;
import sopt.jeolloga.domain.auth.jwt.JwtCookieProvider;
import sopt.jeolloga.domain.auth.service.LoginService;
import sopt.jeolloga.domain.auth.service.LogoutService;
import sopt.jeolloga.domain.auth.service.ReissueService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v2")
public class OAuthController {
    private final LoginService loginService;
    private final LogoutService logoutService;
    private final ReissueService reissueService;
    private final JwtCookieProvider jwtCookieProvider;

    @GetMapping("/auth/login")
    public ResponseEntity<ApiResponse<?>> login(@RequestParam String code) {
        LoginResult result = loginService.login(new LoginCommand(code));
        return okWithCookies("로그인 성공", jwtCookieProvider.createAllCookies(result));
    }

    @PostMapping("/auth/reissue")
    public ResponseEntity<ApiResponse<?>> reissue(HttpServletRequest request) {
        String refreshToken = jwtCookieProvider.extractRefreshToken(request);
        LoginResult result = reissueService.reissue(refreshToken);
        List<ResponseCookie> cookies = List.of(
                jwtCookieProvider.createAccessTokenCookie(result.accessToken()),
                jwtCookieProvider.createRefreshTokenCookie(result.refreshToken())
        );
        return okWithCookies("토큰 재발급 완료", cookies);
    }

    @PostMapping("/user/auth/logout")
    public ResponseEntity<ApiResponse<?>> logout(HttpServletRequest request) {
        String accessToken = jwtCookieProvider.extractAccessToken(request);
        logoutService.logout(accessToken);
        return okWithCookies("로그아웃 성공", jwtCookieProvider.deleteAllCookies());
    }

    @PostMapping("/user/auth/unlink")
    public ResponseEntity<ApiResponse<?>> unlink(HttpServletRequest request) {
        String kakaoAccessToken = jwtCookieProvider.extractKakaoToken(request);
        loginService.unlinkFromKakao(kakaoAccessToken);
        return okWithCookies("회원 탈퇴 및 카카오 연결 해제 완료", jwtCookieProvider.deleteAllCookies());
    }

    private ResponseEntity.BodyBuilder withCookies(List<ResponseCookie> cookies) {
        ResponseEntity.BodyBuilder builder = ResponseEntity.ok();
        for (ResponseCookie cookie : cookies) {
            builder.header(HttpHeaders.SET_COOKIE, cookie.toString());
        }
        return builder;
    }

    private ResponseEntity<ApiResponse<?>> okWithCookies(String message, List<ResponseCookie> cookies) {
        return withCookies(cookies).body(ApiResponse.success(message));
    }

}
