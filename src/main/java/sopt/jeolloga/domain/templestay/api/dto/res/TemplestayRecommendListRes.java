package sopt.jeolloga.domain.templestay.api.dto.res;

import java.util.List;

public record TemplestayRecommendListRes(
        List<TemplestayRecommendRes> recommendTemplestays
) {
}
