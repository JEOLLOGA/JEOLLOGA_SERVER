package sopt.jeolloga.domain.auth;

public record KakaoAccount(String email, Profile profile) {
    public record Profile(String nickname) {

    }
}
