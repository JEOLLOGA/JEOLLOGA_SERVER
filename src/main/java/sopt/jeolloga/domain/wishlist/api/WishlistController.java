package sopt.jeolloga.domain.wishlist.api;

import org.springframework.web.bind.annotation.RestController;
import sopt.jeolloga.domain.wishlist.core.WishlistService;

@RestController
public class WishlistController {
    private final WishlistService wishlistService;

    public WishlistController(WishlistService wishlistService) {
        this.wishlistService = wishlistService;
    }
}
