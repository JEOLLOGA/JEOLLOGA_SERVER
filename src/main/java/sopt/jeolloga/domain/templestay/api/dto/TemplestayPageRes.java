package sopt.jeolloga.domain.templestay.api.dto;

import java.util.List;

public record TemplestayPageRes(
        int currentPage,
        int size,
        long totalElements,
        int totalPages,
        boolean last,
        List<TemplestayRes> content
        ) {
    public static TemplestayPageRes of(List<TemplestayRes> content, int page, int size, long totalElements) {
        int totalPages = (int) Math.ceil((double) totalElements / size);
        boolean last = page >= totalPages;
        return new TemplestayPageRes(page, size, totalElements, totalPages, last, content);
    }
}