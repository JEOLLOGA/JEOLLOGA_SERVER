package sopt.jeolloga.domain.auth.jwt;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;
import sopt.jeolloga.domain.auth.dto.LoginResult;
import sopt.jeolloga.exception.JwtAuthenticationException;

import java.util.Arrays;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtCookieProvider {
    private final JwtProperties jwtProperties;

    private static final String ACCESS_TOKEN_NAME = "accessToken";
    private static final String REFRESH_TOKEN_NAME = "refreshToken";
    private static final String KAKAO_TOKEN_NAME = "kakaoAccessToken";

    public String extractAccessToken(HttpServletRequest request) {
        String token = extractCookie(request, ACCESS_TOKEN_NAME);
        return (token == null || token.isBlank()) ? null : token;
    }

    public String extractRefreshToken(HttpServletRequest request) {
        return extractCookie(request, REFRESH_TOKEN_NAME);
    }

    public String extractKakaoToken(HttpServletRequest request) {
        return extractCookie(request, KAKAO_TOKEN_NAME);
    }

    public List<ResponseCookie> createAllCookies(LoginResult result, HttpServletRequest request) {
        return List.of(
                createCookie(ACCESS_TOKEN_NAME, result.accessToken(), jwtProperties.accessExpiration(), request),
                createCookie(REFRESH_TOKEN_NAME, result.refreshToken(), jwtProperties.refreshExpiration(), request),
                createCookie(KAKAO_TOKEN_NAME, result.kakaoAccessToken(), jwtProperties.kakaoExpiration(), request)
        );
    }

    public List<ResponseCookie> deleteAllCookies(HttpServletRequest request) {
        return List.of(
                deleteCookie(ACCESS_TOKEN_NAME, request),
                deleteCookie(REFRESH_TOKEN_NAME, request),
                deleteCookie(KAKAO_TOKEN_NAME, request)
        );
    }

    public List<ResponseCookie> recreateAccessAndRefreshCookies(LoginResult result, HttpServletRequest request) {
        return List.of(
                createCookie(ACCESS_TOKEN_NAME, result.accessToken(), jwtProperties.accessExpiration(), request),
                createCookie(REFRESH_TOKEN_NAME, result.refreshToken(), jwtProperties.refreshExpiration(), request)
        );
    }

    private ResponseCookie createCookie(String name, String value, long maxAge, HttpServletRequest request) {
        return ResponseCookie.from(name, value)
                .httpOnly(true)
                .secure(true)
                .sameSite("None")
                .path("/")
                .maxAge(maxAge)
                .build();
    }

    private ResponseCookie deleteCookie(String name, HttpServletRequest request) {
        return ResponseCookie.from(name, "")
                .httpOnly(true)
                .secure(true)
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
