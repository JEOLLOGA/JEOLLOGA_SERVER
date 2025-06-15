package sopt.jeolloga.domain.auth.dto;

public record LoginTokens(
        String accessToken,
        String refreshToken
) {
}
