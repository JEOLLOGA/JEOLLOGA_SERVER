package sopt.jeolloga.domain.review.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sopt.jeolloga.domain.review.core.Review;
import sopt.jeolloga.domain.review.core.repository.querydsl.ReviewCustomRepository;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long>, ReviewCustomRepository {
    boolean existsByReviewLink(String reviewLink);
}
