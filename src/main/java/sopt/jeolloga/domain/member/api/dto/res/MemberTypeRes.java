package sopt.jeolloga.domain.member.api.dto.res;

public record MemberTypeRes(
        Long userId,
        String code,
        String tagline,
        String description,
        String requirement,
        String bestMate,
        String worstMate
) {}
