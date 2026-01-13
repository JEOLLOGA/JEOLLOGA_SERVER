package sopt.jeolloga.domain.auth.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import sopt.jeolloga.exception.BusinessException;

import java.io.IOException;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenValidator validator;
    private final JwtTokenGenerator generator;
    private final JwtCookieProvider jwtCookieProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String accessToken = jwtCookieProvider.extractAccessToken(request);

        if (accessToken != null && accessToken.split("\\.").length == 3) {
            try {
                validator.validate(accessToken);
                Authentication auth = generator.getAuthentication(accessToken);
                SecurityContextHolder.getContext().setAuthentication(auth);
            } catch (BusinessException e) {
                log.warn("서버 accessToken 검증 실패: {}", e.getMessage());
            }
        }

        filterChain.doFilter(request, response);
    }


    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String path = request.getRequestURI();
        String method = request.getMethod();

        boolean isTemplestayTypePost =
                "POST".equalsIgnoreCase(method) && (
                        "/api/templestay/type".equals(path) ||
                                "/v2/api/templestay/type".equals(path)
                );

        if (isTemplestayTypePost) {
            return false;
        }

        return path.startsWith("/api/")
                || path.startsWith("/v2/api/")
                || path.startsWith("/auth/")
                || path.startsWith("/v2/auth");
    }

}
