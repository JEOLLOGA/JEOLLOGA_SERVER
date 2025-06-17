package sopt.jeolloga.domain.auth.service;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import sopt.jeolloga.domain.auth.dto.LoginCommand;
import sopt.jeolloga.domain.auth.dto.LoginResult;
import sopt.jeolloga.domain.auth.jwt.JwtTokenGenerator;
import sopt.jeolloga.domain.auth.kakao.RedirectUriResolver;
import sopt.jeolloga.domain.auth.kakao.dto.KakaoTokenRes;
import sopt.jeolloga.domain.auth.kakao.dto.KakaoUserRes;
import sopt.jeolloga.domain.auth.kakao.OauthClientApi;
import sopt.jeolloga.domain.member.Member;
import sopt.jeolloga.domain.member.core.MemberRepository;
import sopt.jeolloga.exception.BusinessErrorCode;
import sopt.jeolloga.exception.BusinessException;

@Service
@RequiredArgsConstructor
@Slf4j
public class LoginService {

    private final OauthClientApi oauthClientApi;
    private final MemberRepository memberRepository;
    private final JwtTokenGenerator jwtTokenGenerator;
    private final TokenService tokenService;
    private final RedirectUriResolver redirectUriResolver;

    public LoginResult login(LoginCommand command, HttpServletRequest request) {
        String redirectUri = redirectUriResolver.resolve(request);

        KakaoTokenRes token = oauthClientApi.fetchToken(command.code(), redirectUri);

        if (token == null || token.accessToken() == null) {
            throw new BusinessException(BusinessErrorCode.KAKAO_CLIENT_ERROR);
        }

        KakaoUserRes user = oauthClientApi.fetchUser(token.accessToken());
        if (user == null || user.id() == null) {
            throw new BusinessException(BusinessErrorCode.KAKAO_CLIENT_ERROR);
        }

        Member member = memberRepository.findByKakaoUserId(user.id())
                .orElseGet(() -> memberRepository.save(user.toEntity()));

        String accessToken = jwtTokenGenerator.generateAccessToken(member.getId());
        String refreshToken = jwtTokenGenerator.generateRefreshToken(member.getId());

        tokenService.save(member.getId(), refreshToken);

        return new LoginResult(accessToken, refreshToken, token.accessToken());
    }

    public void unlinkFromKakao(String kakaoAccessToken) {
        Long kakaoUserId = oauthClientApi.unlink(kakaoAccessToken);
        if (kakaoUserId == null) {
            throw new BusinessException(BusinessErrorCode.KAKAO_CLIENT_ERROR);
        }

        Member member = memberRepository.findByKakaoUserId(kakaoUserId)
                .orElseThrow(() -> new BusinessException(BusinessErrorCode.NOT_FOUND_USER));

        tokenService.delete(member.getId());
        memberRepository.delete(member);
    }
}
