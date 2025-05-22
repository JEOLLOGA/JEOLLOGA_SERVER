package sopt.jeolloga.domain.templestay.api.dto;

import java.util.List;

public record TemplestayRecommendListRes(
        List<TemplestayRecommendRes> recommendTemplestays
) {
}
