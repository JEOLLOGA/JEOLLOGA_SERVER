package sopt.jeolloga.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import sopt.jeolloga.domain.auth.kakao.KakaoOauthProperties;

@Configuration
@EnableConfigurationProperties(KakaoOauthProperties.class)
public class OauthConfig {
}
