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

@RestController
@RequestMapping("/api/auth/kakao")
@RequiredArgsConstructor
public class OAuthController {
    private final LoginService loginService;
    private final LogoutService logoutService;
    private final ReissueService reissueService;
    private final JwtCookieProvider jwtCookieProvider;

    @GetMapping("/login")
    public ResponseEntity<ApiResponse<?>> login(@RequestParam String code) {
        LoginResult result = loginService.login(new LoginCommand(code));

        ResponseCookie accessTokenCookie = jwtCookieProvider.createAccessTokenCookie(result.accessToken());
        ResponseCookie refreshTokenCookie = jwtCookieProvider.createRefreshTokenCookie(result.refreshToken());
        ResponseCookie kakoTokenCookie = jwtCookieProvider.createKakaoTokenCookie(result.kakaoAccessToken());

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE,
                        accessTokenCookie.toString(),
                        refreshTokenCookie.toString(),
                        kakoTokenCookie.toString())
                .body(ApiResponse.success("로그인 성공"));
    }

    @PostMapping("/logout")
    public ResponseEntity<ApiResponse<String>> logout(HttpServletRequest request) {
        String accessToken = jwtCookieProvider.extractAccessToken(request);
        logoutService.logout(accessToken);

        ResponseCookie accessTokenCookie = jwtCookieProvider.deleteAccessTokenCookie();
        ResponseCookie refreshTokenCookie = jwtCookieProvider.deleteRefreshTokenCookie();
        ResponseCookie kakoTokenCookie = jwtCookieProvider.deleteKakaoTokenCookie();

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE,
                        accessTokenCookie.toString(),
                        refreshTokenCookie.toString(),
                        kakoTokenCookie.toString())
                .body(ApiResponse.success("로그아웃 성공"));
    }

    @PostMapping("/reissue")
    public ResponseEntity<ApiResponse<?>> reissue(HttpServletRequest request) {
        String refreshToken = jwtCookieProvider.extractRefreshToken(request);
        LoginResult result = reissueService.reissue(refreshToken);

        ResponseCookie newAccessTokenCookie = jwtCookieProvider.createAccessTokenCookie(result.accessToken());
        ResponseCookie newRefreshTokenCookie = jwtCookieProvider.createRefreshTokenCookie(result.refreshToken());

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE,
                        newAccessTokenCookie.toString(),
                        newRefreshTokenCookie.toString())
                .body(ApiResponse.success("토큰 재발급 완료"));
    }

    @PostMapping("/unlink")
    public ResponseEntity<ApiResponse<?>> unlink(HttpServletRequest request) {
        String kakaoAccessToken = jwtCookieProvider.extractKakaoToken(request);
        loginService.unlinkFromKakao(kakaoAccessToken);

        ResponseCookie deleteAccess = jwtCookieProvider.deleteAccessTokenCookie();
        ResponseCookie deleteRefresh = jwtCookieProvider.deleteRefreshTokenCookie();
        ResponseCookie deleteKakao = jwtCookieProvider.deleteKakaoTokenCookie();

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE,
                        deleteAccess.toString(),
                        deleteRefresh.toString(),
                        deleteKakao.toString())
                .body(ApiResponse.success("회원 탈퇴 및 카카오 연결 해제 완료"));
    }
}
