package sopt.jeolloga.domain.templestay.recommend;

public record TemplestayPickRes(
        Long templestayId,
        String region,
        String type,
        String templestayName,
        String templeName,
        String imgUrl
) {}
