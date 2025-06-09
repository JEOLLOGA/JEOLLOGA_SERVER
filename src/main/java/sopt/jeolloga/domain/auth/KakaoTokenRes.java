package sopt.jeolloga.domain.auth;

import com.fasterxml.jackson.annotation.JsonProperty;

public record KakaoTokenRes(
        @JsonProperty("access_token") String accessToken,
        @JsonProperty("refresh_token") String refreshToken
) {

}
