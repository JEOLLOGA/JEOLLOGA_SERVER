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
        boolean last = page >= totalPages;
        return new WishlistPageRes(page, size, totalElements, totalPages, last, content);
    }
}