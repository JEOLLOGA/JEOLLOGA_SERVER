package sopt.jeolloga.domain.templestay.api.dto.res;

public record TemplestayRes(
        Long templestayId,
        String templeName,
        String templestayName,
        String region,
        String type,
        String imgUrl,
        boolean wish
) {
}
