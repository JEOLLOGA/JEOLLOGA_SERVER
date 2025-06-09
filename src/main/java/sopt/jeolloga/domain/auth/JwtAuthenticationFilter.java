package sopt.jeolloga.domain.auth;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenValidator validator;
    private final JwtTokenExtractor extractor;
    private final JwtTokenGenerator generator;

    public JwtAuthenticationFilter(JwtTokenValidator validator, JwtTokenExtractor extractor, JwtTokenGenerator generator) {
        this.validator = validator;
        this.extractor = extractor;
        this.generator = generator;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String token = extractor.extract(request);
        if (token != null) {
            validator.validate(token);
            Authentication auth = generator.getAuthentication(token);
            SecurityContextHolder.getContext().setAuthentication(auth);
        }
        filterChain.doFilter(request, response);
    }
}
