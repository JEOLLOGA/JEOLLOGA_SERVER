package sopt.jeolloga.domain.auth.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtTokenGenerator {

    private final JwtProperties jwtProperties;

    public Authentication getAuthentication(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(jwtProperties.secret().getBytes(StandardCharsets.UTF_8))
                .build()
                .parseClaimsJws(token)
                .getBody();

        Long userId = Long.parseLong(claims.getSubject());
        return new UsernamePasswordAuthenticationToken(userId, "", List.of());
    }

    public Long extractUserId(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(jwtProperties.secret().getBytes(StandardCharsets.UTF_8))
                .build()
                .parseClaimsJws(token)
                .getBody();

        return Long.parseLong(claims.getSubject());
    }

    public String generateRefreshToken(Long userId) {
        Date now = new Date();
        Date expiry = new Date(now.getTime() + jwtProperties.refreshExpiration());

        return Jwts.builder()
                .setSubject(userId.toString())
                .setIssuedAt(now)
                .setExpiration(expiry)
                .signWith(SignatureAlgorithm.HS256, jwtProperties.secret().getBytes(StandardCharsets.UTF_8))
                .compact();
    }

    public String generateAccessToken(Long userId) {
        Date now = new Date();
        Date expiry = new Date(now.getTime() + jwtProperties.accessExpiration());

        return Jwts.builder()
                .setSubject(String.valueOf(userId))
                .setIssuedAt(now)
                .setExpiration(expiry)
                .signWith(SignatureAlgorithm.HS256, jwtProperties.secret().getBytes(StandardCharsets.UTF_8))
                .compact();
    }
}