package sopt.jeolloga.domain.auth;

import com.fasterxml.jackson.annotation.JsonProperty;
import sopt.jeolloga.domain.member.Member;

import java.util.Optional;

public record KakaoUserRes(
        Long id,
        @JsonProperty("kakao_account") KakaoAccount kakaoAccount
) {
    public Member toEntity() {
        return new Member(id,
                Optional.ofNullable(kakaoAccount).map(KakaoAccount::email).orElse(""),
                Optional.ofNullable(kakaoAccount).map(KakaoAccount::profile).map(KakaoAccount.Profile::nickname).orElse(""));
    }
}
