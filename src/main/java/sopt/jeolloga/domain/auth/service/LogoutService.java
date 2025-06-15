package sopt.jeolloga.domain.auth.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sopt.jeolloga.domain.auth.jwt.JwtTokenGenerator;
import sopt.jeolloga.exception.BusinessErrorCode;
import sopt.jeolloga.exception.BusinessException;

@Service
@RequiredArgsConstructor
public class LogoutService {

    private final TokenService tokenService;
    private final JwtTokenGenerator jwtTokenGenerator;

    public void logout(String accessToken) {
        try {
            Long userId = jwtTokenGenerator.extractUserId(accessToken);
            tokenService.delete(userId);
        } catch (Exception e) {
            throw new BusinessException(BusinessErrorCode.INVALID_SERVER_JWT);
        }
    }
}
