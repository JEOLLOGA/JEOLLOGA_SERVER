package sopt.jeolloga.domain.review;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sopt.jeolloga.common.dto.ApiResponse;
import sopt.jeolloga.domain.templestay.core.repository.TemplestayRepository;
import sopt.jeolloga.exception.BusinessException;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReviewService {

    private final TemplestayRepository templestayRepository;
    private final ReviewRepository reviewRepository;
    private final NaverBlogClient naverBlogClient;

    private static final int BATCH_SIZE = 10;
    private static final int SLEEP_MILLIS = 300;

    @Transactional
    public ApiResponse<?> saveTempleReviews(int page) {
        List<String> templeNames = templestayRepository.findDistinctTempleNames();
        int start = page * BATCH_SIZE;
        int end = Math.min(start + BATCH_SIZE, templeNames.size());

        if (start >= end) {
            log.info("요청된 페이지 범위에 templeName 없음");
            return ApiResponse.success("처리할 templeName 없음");
        }

        for (int i = start; i < end; i++) {
            String templeName = templeNames.get(i);
            try {
                List<TemplestayVO> blogs = naverBlogClient.fetchBlogs(templeName);
                List<Review> reviews = blogs.stream()
                        .filter(b -> b.link() != null && b.link().contains("naver.com"))
                        .filter(b -> b.postdate() != null && b.postdate().compareTo("20220101") > 0)
                        .map(b -> b.toEntity(templeName))
                        .filter(r -> !reviewRepository.existsByReviewLink(r.getReviewLink()))
                        .toList();

                if (!reviews.isEmpty()) {
                    reviewRepository.saveAll(reviews);
                }

                Thread.sleep(SLEEP_MILLIS);

            } catch (BusinessException e) {
                if (e.getMessage().contains("429")) {
                    log.warn("호출 제한 초과, 스킵됨", templeName);
                } else {
                    log.error("리뷰 저장 실패: {}", templeName, e.getMessage());
                }
            } catch (Exception e) {
                log.error("처리 중 예외 발생: {}", templeName, e.getMessage());
            }
        }

        return ApiResponse.success("temple 리뷰 처리 완료".formatted(start + 1, end));
    }
}
