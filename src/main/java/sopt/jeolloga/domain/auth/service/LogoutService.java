package sopt.jeolloga.domain.auth.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sopt.jeolloga.domain.auth.jwt.JwtTokenGenerator;

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
