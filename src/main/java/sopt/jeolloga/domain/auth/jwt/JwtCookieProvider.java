package sopt.jeolloga.domain.auth.jwt;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;
import sopt.jeolloga.domain.auth.dto.LoginResult;

import java.util.Arrays;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtCookieProvider {
    private final JwtProperties jwtProperties;

    private static final String ACCESS_TOKEN_NAME = "accessToken";
    private static final String REFRESH_TOKEN_NAME = "refreshToken";
    private static final String KAKAO_TOKEN_NAME = "kakaoAccessToken";

    public ResponseCookie createAccessTokenCookie(String accessToken) {
        return createCookie(ACCESS_TOKEN_NAME, accessToken, (int) jwtProperties.accessExpiration());
    }

    public ResponseCookie createRefreshTokenCookie(String refreshToken) {
        return createCookie(REFRESH_TOKEN_NAME, refreshToken, (int) jwtProperties.refreshExpiration());
    }

    public ResponseCookie createKakaoTokenCookie(String value) {
        return createCookie(KAKAO_TOKEN_NAME, value, (int) jwtProperties.kakaoExpiration());
    }

    public ResponseCookie deleteAccessTokenCookie() {
        return deleteCookie(ACCESS_TOKEN_NAME);
    }

    public ResponseCookie deleteRefreshTokenCookie() {
        return deleteCookie(REFRESH_TOKEN_NAME);
    }

    public ResponseCookie deleteKakaoTokenCookie() {
        return deleteCookie(KAKAO_TOKEN_NAME);
    }

    public String extractAccessToken(HttpServletRequest request) {
        return extractCookie(request, ACCESS_TOKEN_NAME);
    }

    public String extractRefreshToken(HttpServletRequest request) {
        return extractCookie(request, REFRESH_TOKEN_NAME);
    }

    public String extractKakaoToken(HttpServletRequest request) {
        return extractCookie(request, KAKAO_TOKEN_NAME);
    }

    public List<ResponseCookie> createAllCookies(LoginResult result) {
        return List.of(
                createAccessTokenCookie(result.accessToken()),
                createRefreshTokenCookie(result.refreshToken()),
                createKakaoTokenCookie(result.kakaoAccessToken())
        );
    }

    public List<ResponseCookie> deleteAllCookies() {
        return List.of(
                deleteAccessTokenCookie(),
                deleteRefreshTokenCookie(),
                deleteKakaoTokenCookie()
        );
    }

    //http로 개발 및 테스트 중일땨는 secure false로 추후 메인배포에서는 true값으로 변경
    private ResponseCookie createCookie(String name, String value, int maxAge) {
        return ResponseCookie.from(name, value)
                .httpOnly(true)
                .secure(false)
                .sameSite("None")
                .path("/")
                .maxAge(maxAge)
                .build();
    }

    private ResponseCookie deleteCookie(String name) {
        return ResponseCookie.from(name, "")
                .httpOnly(true)
                .secure(false)
                .sameSite("None")
                .path("/")
                .maxAge(0)
                .build();
    }

    private String extractCookie(HttpServletRequest request, String name) {
        if (request.getCookies() == null) {
            return null;
        }

        return Arrays.stream(request.getCookies())
                .filter(cookie -> cookie.getName().equals(name))
                .map(cookie -> cookie.getValue())
                .findFirst()
                .orElse(null);
    }
}
