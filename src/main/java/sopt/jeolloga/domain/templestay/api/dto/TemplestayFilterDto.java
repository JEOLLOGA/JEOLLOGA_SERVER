package sopt.jeolloga.domain.templestay.api.dto;

public record TemplestayFilterDto(
        Long templestayId,
        String templeName,
        String templestayName,
        int region,
        int type,
        int wishCount
) {}
