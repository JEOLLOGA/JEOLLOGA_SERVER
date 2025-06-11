package sopt.jeolloga.domain.auth.jwt;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import sopt.jeolloga.exception.BusinessErrorCode;
import sopt.jeolloga.exception.BusinessException;

import java.nio.charset.StandardCharsets;

@Component
@RequiredArgsConstructor
class JwtTokenValidator {
    private final JwtProperties jwtProperties;

    public void validate(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(jwtProperties.secret().getBytes(StandardCharsets.UTF_8))
                    .build()
                    .parseClaimsJws(token);
        } catch (JwtException | IllegalArgumentException e) {
            throw new BusinessException(BusinessErrorCode.KAKAO_CLIENT_ERROR);
        }
    }
}
