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

        int safePage = Math.max(page, 1);
        boolean last = totalPages == 0 || safePage >= totalPages;

        return new TemplestayPageRes(
                safePage,
                size,
                totalElements,
                totalPages,
                last,
                content != null ? content : List.of()
        );
    }
}