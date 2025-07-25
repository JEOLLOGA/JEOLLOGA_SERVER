package sopt.jeolloga.domain.auth.kakao;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import sopt.jeolloga.domain.auth.kakao.dto.KaKaoUnlinkRes;
import sopt.jeolloga.domain.auth.kakao.dto.KakaoTokenRes;
import sopt.jeolloga.domain.auth.kakao.dto.KakaoUserRes;
import sopt.jeolloga.exception.BusinessErrorCode;
import sopt.jeolloga.exception.BusinessException;
import org.springframework.http.HttpStatusCode;

@Component
@RequiredArgsConstructor
@Slf4j
public class OauthClientApi {

    private final WebClient kakaoAuthClient; // baseUrl: https://kauth.kakao.com
    private final WebClient kakaoApiClient;  // baseUrl: https://kapi.kakao.com
    private final KakaoOauthProperties properties;

    public KakaoTokenRes fetchToken(String code, String redirectUri) {
        try {
            return kakaoAuthClient.post()
                    .uri("/oauth/token")
                    .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                    .body(BodyInserters.fromFormData("grant_type", "authorization_code")
                            .with("client_id", properties.clientId())
                            .with("redirect_uri", redirectUri)
                            .with("code", code)
                            .with("client_secret", properties.clientSecret()))
                    .retrieve()
                    .onStatus(HttpStatusCode::isError, response ->
                            response.bodyToMono(String.class).flatMap(errorBody -> {
                                log.error("카카오 토큰 요청 실패 - 상태: {}, 응답: {}", response.statusCode(), errorBody);
                                return Mono.error(new BusinessException(BusinessErrorCode.KAKAO_CLIENT_ERROR));
                            })
                    )
                    .bodyToMono(KakaoTokenRes.class)
                    .block();
        } catch (Exception e) {
            log.error("카카오 토큰 요청 중 예외 발생", e);
            throw new BusinessException(BusinessErrorCode.KAKAO_CLIENT_ERROR);
        }
    }

    public KakaoUserRes fetchUser(String accessToken) {
        try {
            return kakaoApiClient.get()
                    .uri("/v2/user/me")
                    .headers(headers -> headers.setBearerAuth(accessToken))
                    .retrieve()
                    .onStatus(HttpStatusCode::isError, response ->
                            response.bodyToMono(String.class).flatMap(errorBody -> {
                                log.error("카카오 사용자 정보 요청 실패 - 응답: {}", errorBody);
                                return Mono.error(new BusinessException(BusinessErrorCode.KAKAO_CLIENT_ERROR));
                            })
                    )
                    .bodyToMono(KakaoUserRes.class)
                    .block();
        } catch (Exception e) {
            log.error("카카오 사용자 정보 요청 중 예외 발생", e);
            throw new BusinessException(BusinessErrorCode.KAKAO_CLIENT_ERROR);
        }
    }

    public Long unlink(String kakaoAccessToken) {
        try {
            return kakaoApiClient.post()
                    .uri("/v1/user/unlink")
                    .headers(headers -> {
                        headers.setBearerAuth(kakaoAccessToken);
                        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
                    })
                    .retrieve()
                    .onStatus(HttpStatusCode::is4xxClientError, response -> {
                        if (response.statusCode().value() == 401) {
                            return response.bodyToMono(String.class).flatMap(errorBody -> {
                                log.warn("Kakao Unlink 401 Unauthorized - Token: {}, 응답: {}",
                                        kakaoAccessToken, errorBody);
                                return Mono.error(new BusinessException(BusinessErrorCode.INVALID_KAKAO_TOKEN));
                            });
                        }
                        return response.bodyToMono(String.class).flatMap(errorBody -> {
                            log.error("Kakao Unlink 4xx Error - 상태: {}, 응답: {}, Token: {}",
                                    response.statusCode(), errorBody, kakaoAccessToken);
                            return Mono.error(new BusinessException(BusinessErrorCode.KAKAO_CLIENT_ERROR));
                        });
                    })
                    .onStatus(HttpStatusCode::is5xxServerError, response ->
                            response.bodyToMono(String.class).flatMap(errorBody -> {
                                log.error("Kakao Unlink 5xx Error - 응답: {}, Token: {}", errorBody, kakaoAccessToken);
                                return Mono.error(new BusinessException(BusinessErrorCode.KAKAO_CLIENT_ERROR));
                            })
                    )
                    .bodyToMono(KaKaoUnlinkRes.class)
                    .map(KaKaoUnlinkRes::id)
                    .block();
        } catch (Exception e) {
            log.error("Kakao Unlink 예외 발생 - Token: {}, 메시지: {}", kakaoAccessToken, e.getMessage(), e);
            throw new BusinessException(BusinessErrorCode.KAKAO_CLIENT_ERROR);
        }
    }
}