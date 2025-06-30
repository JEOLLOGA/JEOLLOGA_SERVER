package sopt.jeolloga.domain.wishlist.api.dto;

import java.util.List;

public record WishlistPageRes(
        int currentPage,
        int size,
        long totalElements,
        int totalPages,
        boolean last,
        List<WishlistRes> content
) {
    public static WishlistPageRes of(List<WishlistRes> content, int page, int size, long totalElements) {
        int totalPages = (int) Math.ceil((double) totalElements / size);

        int safePage = Math.max(page, 1);

        boolean last = totalPages == 0 || safePage >= totalPages;

        return new WishlistPageRes(
                safePage,
                size,
                totalElements,
                totalPages,
                last,
                content != null ? content : List.of()
        );
    }
}