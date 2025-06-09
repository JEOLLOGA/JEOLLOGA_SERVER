package sopt.jeolloga.domain.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sopt.jeolloga.exception.BusinessErrorCode;
import sopt.jeolloga.exception.BusinessException;

@Service
@RequiredArgsConstructor
public class ReissueService {
    private final TokenService tokenService;
    private final JwtTokenGenerator jwtTokenGenerator;

    public LoginResult reissue(Long userId, String refreshToken) {
        if (!tokenService.validate(userId, refreshToken)) {
            throw new BusinessException(BusinessErrorCode.KAKAO_UNAUTHORIZED_REFRESHTOKEN);
        }
        String newAccess = jwtTokenGenerator.generateAccessToken(userId);
        String newRefresh = jwtTokenGenerator.generateRefreshToken();
        tokenService.save(userId, newRefresh);
        return new LoginResult(newAccess, newRefresh);
    }
}
