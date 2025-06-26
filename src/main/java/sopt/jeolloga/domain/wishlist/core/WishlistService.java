package sopt.jeolloga.domain.wishlist.core;

import org.springframework.stereotype.Service;

@Service
public class WishlistService {
    private final WishlistRepository wishlistRepository;

    public WishlistService(WishlistRepository wishlistRepository) {
        this.wishlistRepository = wishlistRepository;
    }
}
