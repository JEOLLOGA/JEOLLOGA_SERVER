package sopt.jeolloga.domain.member.api.dto.res;

public record TypeResultRes(
        String code,
        String tagline,
        String description,
        String requirement,
        String bestMate,
        String worstMate
) {}
