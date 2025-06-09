package sopt.jeolloga.domain.auth;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

@Component
@RequiredArgsConstructor
@Slf4j
public class OauthClientApi {

    private final WebClient webClient;
    private final KakaoOauthProperties properties;

    public KakaoTokenRes fetchToken(String code) {
        log.info("인가 코드: {}", code);
        log.info("client_id: {}", properties.clientId());
        log.info("redirect_uri: {}", properties.redirectUri());
        return webClient.post()
                .uri("/oauth/token")
                .body(BodyInserters.fromFormData("grant_type", "authorization_code")
                        .with("client_id", properties.clientId())
                        .with("redirect_uri", properties.redirectUri())
                        .with("code", code)
                        .with("client_secret", properties.clientSecret()))
                .retrieve()
                .bodyToMono(KakaoTokenRes.class)
                .block();
    }

    public KakaoUserRes fetchUser(String accessToken) {
        return WebClient.create("https://kapi.kakao.com")
                .get()
                .uri("/v2/user/me")
                .headers(headers -> headers.setBearerAuth(accessToken))
                .retrieve()
                .bodyToMono(KakaoUserRes.class)
                .block();
    }
}
