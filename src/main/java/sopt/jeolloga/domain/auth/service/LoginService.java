package sopt.jeolloga.domain.auth.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sopt.jeolloga.domain.auth.dto.LoginCommand;
import sopt.jeolloga.domain.auth.dto.LoginResult;
import sopt.jeolloga.domain.auth.jwt.JwtTokenGenerator;
import sopt.jeolloga.domain.auth.kakao.dto.KakaoTokenRes;
import sopt.jeolloga.domain.auth.kakao.dto.KakaoUserRes;
import sopt.jeolloga.domain.auth.kakao.OauthClientApi;
import sopt.jeolloga.domain.member.Member;
import sopt.jeolloga.domain.member.core.MemberRepository;

@Service
@RequiredArgsConstructor
public class LoginService {
    private final OauthClientApi oauthClientApi;
    private final MemberRepository memberRepository;
    private final JwtTokenGenerator jwtTokenGenerator;
    private final TokenService tokenService;

    public LoginResult login(LoginCommand command) {
        KakaoTokenRes token = oauthClientApi.fetchToken(command.code());
        KakaoUserRes user = oauthClientApi.fetchUser(token.accessToken());

        Member member = memberRepository.findByKakaoUserId(user.id())
                .orElseGet(() -> memberRepository.save(user.toEntity()));

        String accessToken = jwtTokenGenerator.generateAccessToken(member.getId());
        String refreshToken = jwtTokenGenerator.generateRefreshToken(member.getId());

        tokenService.save(member.getId(), refreshToken);

        return new LoginResult(accessToken, refreshToken);
    }
}
