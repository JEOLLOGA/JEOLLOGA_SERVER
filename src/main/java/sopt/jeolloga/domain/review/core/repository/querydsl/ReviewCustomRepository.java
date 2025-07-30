package sopt.jeolloga.domain.review.core.repository.querydsl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import sopt.jeolloga.domain.review.core.Review;

public interface ReviewCustomRepository {
    Page<Review> findByTempleName(String templeName, Pageable pageable);
}
