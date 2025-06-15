package sopt.jeolloga.domain.auth.jwt;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "jwt")
public record JwtProperties(String secret, long accessExpiration, long refreshExpiration, long kakaoExpiration
) {}

