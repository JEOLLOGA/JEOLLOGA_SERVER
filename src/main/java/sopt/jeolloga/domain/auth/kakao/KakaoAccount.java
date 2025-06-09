package sopt.jeolloga.domain.auth.kakao;

public record KakaoAccount(String email, Profile profile) {
    public record Profile(String nickname) {

    }
}
