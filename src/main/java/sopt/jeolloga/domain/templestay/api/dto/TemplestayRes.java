package sopt.jeolloga.domain.templestay.api.dto;

public record TemplestayRes(
        Long templestayId,
        String templeName,
        String TemplestayName,
        String region,
        String type,
        boolean wish
) {
}
