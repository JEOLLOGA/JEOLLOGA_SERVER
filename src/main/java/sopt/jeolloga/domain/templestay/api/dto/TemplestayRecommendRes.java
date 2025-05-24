package sopt.jeolloga.domain.templestay.api.dto;

public record TemplestayRecommendRes(
        Long id,
        int rank,
        String templestayName,
        String imgUrl,
        String region,
        String templeName
) {
}
