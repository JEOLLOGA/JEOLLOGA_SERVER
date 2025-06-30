package sopt.jeolloga.domain.wishlist.core.querydsl;

import sopt.jeolloga.domain.wishlist.api.dto.WishlistRes;

import java.util.List;

public interface WishlistCustomRepository {
    List<WishlistRes> findWishlistContent(Long userId, int offset, int limit);
    long countWishlist(Long userId);
}
