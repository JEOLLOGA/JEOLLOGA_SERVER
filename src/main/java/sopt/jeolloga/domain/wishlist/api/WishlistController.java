package sopt.jeolloga.domain.wishlist.api;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sopt.jeolloga.common.dto.ApiResponse;
import sopt.jeolloga.domain.auth.jwt.JwtCookieProvider;
import sopt.jeolloga.domain.auth.jwt.JwtTokenGenerator;
import sopt.jeolloga.domain.wishlist.core.WishlistService;

@RestController
@RequestMapping("/user")
public class WishlistController {
    private final WishlistService wishlistService;
    private final JwtCookieProvider jwtCookieProvider;
    private final JwtTokenGenerator jwtTokenGenerator;

    public WishlistController(WishlistService wishlistService, JwtCookieProvider jwtCookieProvider, JwtTokenGenerator jwtTokenGenerator) {
        this.wishlistService = wishlistService;
        this.jwtCookieProvider = jwtCookieProvider;
        this.jwtTokenGenerator = jwtTokenGenerator;
    }

    @PostMapping("/wish/{id}")
    public ResponseEntity<ApiResponse<?>> updateWishlist(
            HttpServletRequest request,
            @PathVariable Long id
    ) {
        String accessToken = jwtCookieProvider.extractAccessToken(request);
        Long userId = jwtTokenGenerator.extractUserId(accessToken);

        wishlistService.updateWishlist(userId, id);
        return ResponseEntity.ok(ApiResponse.success(null));
    }
}
