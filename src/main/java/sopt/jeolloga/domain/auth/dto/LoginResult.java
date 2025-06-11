package sopt.jeolloga.domain.auth.dto;

public record LoginResult(
        String accessToken,
        String refreshToken,
        String kakaoAccessToken
) {

}
