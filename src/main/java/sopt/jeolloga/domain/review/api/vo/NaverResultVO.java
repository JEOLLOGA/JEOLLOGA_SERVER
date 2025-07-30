package sopt.jeolloga.domain.review.api.vo;

import java.util.List;

public record NaverResultVO(
        String lastBuildDate,
        int total,
        int start,
        int display,
        List<TemplestayVO> items
) {
}
