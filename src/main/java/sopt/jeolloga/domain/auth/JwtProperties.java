package sopt.jeolloga.domain.auth;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "jwt")
public record JwtProperties(String secret, long accessExpiration, long refreshExpiration) {}

