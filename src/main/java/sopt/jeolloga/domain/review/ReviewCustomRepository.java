package sopt.jeolloga.domain.review;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ReviewCustomRepository {
    Page<Review> findByTempleName(String templeName, Pageable pageable);
}
