package sopt.jeolloga.domain.review.core.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import sopt.jeolloga.domain.review.api.vo.NaverResultVO;
import sopt.jeolloga.domain.review.api.vo.TemplestayVO;
import sopt.jeolloga.exception.BusinessErrorCode;
import sopt.jeolloga.exception.BusinessException;

import java.net.URI;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class NaverBlogClient {

    @Value("${naver.api.client-id}")
    private String clientId;

    @Value("${naver.api.client-secret}")
    private String clientSecret;

    public List<TemplestayVO> fetchBlogs(String templeName) {
        try {
            URI uri = UriComponentsBuilder
                    .fromUriString("https://openapi.naver.com")
                    .path("/v1/search/blog.json")
                    .queryParam("query", templeName + " 템플스테이")
                    .queryParam("display", 20)
                    .queryParam("start", 1)
                    .queryParam("sort", "sim")
                    .encode()
                    .build()
                    .toUri();

            RequestEntity<Void> requestEntity = RequestEntity
                    .get(uri)
                    .header("X-Naver-Client-Id", clientId)
                    .header("X-Naver-Client-Secret", clientSecret)
                    .build();

            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<String> response = restTemplate.exchange(requestEntity, String.class);

            if (!response.getStatusCode().is2xxSuccessful()) {
                throw new BusinessException(BusinessErrorCode.API_CALL_FAILED);
            }

            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(response.getBody(), NaverResultVO.class).items();
        } catch (JsonProcessingException e) {
            throw new BusinessException(BusinessErrorCode.API_CALL_FAILED);
        }
    }
}
