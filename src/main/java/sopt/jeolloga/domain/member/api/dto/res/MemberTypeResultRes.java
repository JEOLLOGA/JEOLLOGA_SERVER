package sopt.jeolloga.domain.member.api.dto.res;

public record MemberTypeResultRes(
        Long userId,
        String code,
        String tagline,
        String description,
        String requirement,
        String bestMate,
        String worstMate
) {
}
