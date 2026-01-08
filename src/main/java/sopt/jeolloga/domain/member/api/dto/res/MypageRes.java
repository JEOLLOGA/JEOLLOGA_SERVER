package sopt.jeolloga.domain.member.api.dto.res;

public record MypageRes(
        String type,
        String typeContent,
        String nickname,
        String email,
        boolean hasType
) {
}
