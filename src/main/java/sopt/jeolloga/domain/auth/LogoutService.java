package sopt.jeolloga.domain.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LogoutService {

    private final TokenService tokenService;
    private final JwtTokenGenerator jwtTokenGenerator;

    public void logout(String bearerToken) {
        String token = bearerToken.replace("Bearer ", "");
        Long userId = jwtTokenGenerator.extractUserId(token);
        tokenService.delete(userId);
    }
}
