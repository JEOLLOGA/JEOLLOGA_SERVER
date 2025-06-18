package sopt.jeolloga.domain.auth.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sopt.jeolloga.domain.auth.jwt.JwtTokenGenerator;
import sopt.jeolloga.domain.auth.dto.LoginResult;
import sopt.jeolloga.domain.member.Member;
import sopt.jeolloga.domain.member.core.MemberRepository;
import sopt.jeolloga.exception.BusinessErrorCode;
import sopt.jeolloga.exception.BusinessException;

@Service
@RequiredArgsConstructor
public class ReissueService {

    private final TokenService tokenService;
    private final JwtTokenGenerator jwtTokenGenerator;
    private final MemberRepository memberRepository;

    public LoginResult reissue(String refreshToken) {
        Long userId;
        try {
            userId = jwtTokenGenerator.extractUserId(refreshToken);
        } catch (Exception e) {
            throw new BusinessException(BusinessErrorCode.INVALID_SERVER_JWT);
        }

        if (!tokenService.validate(userId, refreshToken)) {
            throw new BusinessException(BusinessErrorCode.KAKAO_UNAUTHORIZED_REFRESHTOKEN);
        }

        String newAccess = jwtTokenGenerator.generateAccessToken(userId);
        String newRefresh = jwtTokenGenerator.generateRefreshToken(userId);
        tokenService.save(userId, newRefresh);

        Member member = memberRepository.findById(userId)
                .orElseThrow(() -> new BusinessException(BusinessErrorCode.NOT_FOUND_USER));

        return new LoginResult(
                newAccess,
                newRefresh,
                null,
                member.getId(),
                member.getNickname()
        );
    }
}
