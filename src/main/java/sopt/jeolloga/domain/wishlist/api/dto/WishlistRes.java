package sopt.jeolloga.domain.wishlist.api.dto;

public record WishlistRes(
        Long templestayId,
        String templeName,
        String templestayName,
        String region,
        String type,
        String imgUrl,
        boolean wish
) {
}
