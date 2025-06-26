package sopt.jeolloga.domain.templestay.api.dto;

import java.util.List;

public record TemplestayListRes(
        long totalCount,
        int currentPage,
        List<TemplestayRes> templestays
        ) {
    public static TemplestayListRes of(List<TemplestayRes> content, long totalCount, int currentPage) {
        return new TemplestayListRes(totalCount, currentPage, content);
    }
}