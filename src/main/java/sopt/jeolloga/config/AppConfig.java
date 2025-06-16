package sopt.jeolloga.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import sopt.jeolloga.domain.auth.kakao.KakaoOauthProperties;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
@EnableConfigurationProperties(KakaoOauthProperties.class)
public class AppConfig {

    @Bean
    public WebClient kakaoAuthClient(WebClient.Builder builder) {
        return builder
                .baseUrl("https://kauth.kakao.com")
                .defaultHeader("Accept", MediaType.APPLICATION_JSON_VALUE)
                .build();
    }

    @Bean
    public WebClient kakaoApiClient(WebClient.Builder builder) {
        return builder
                .baseUrl("https://kapi.kakao.com")
                .defaultHeader("Accept", MediaType.APPLICATION_JSON_VALUE)
                .build();
    }
}
