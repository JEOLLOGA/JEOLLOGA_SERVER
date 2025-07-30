package sopt.jeolloga.domain.review.core.repository.querydsl;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import sopt.jeolloga.domain.review.api.dto.ReviewPageRes;
import sopt.jeolloga.domain.review.api.dto.ReviewRes;
import sopt.jeolloga.domain.review.core.QReview;
import sopt.jeolloga.domain.review.core.Review;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ReviewRepositoryImpl implements ReviewCustomRepository {

    private final JPAQueryFactory queryFactory;
    private final QReview review = QReview.review;

    @Override
    public Page<Review> findByTempleName(String templeName, Pageable pageable) {
        List<Review> content = queryFactory.selectFrom(review)
                .where(review.templeName.eq(templeName))
                .orderBy(review.reviewDate.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        long count = queryFactory.select(review.count())
                .from(review)
                .where(review.templeName.eq(templeName))
                .fetchOne();

        return new PageImpl<>(content, pageable, count);
    }

    @Override
    public ReviewPageRes findReviewsByTempleName(String templeName, int page, int pageSize) {
        long total = queryFactory
                .select(review.count())
                .from(review)
                .where(review.templeName.eq(templeName))
                .fetchOne();

        List<ReviewRes> reviews = queryFactory
                .select(Projections.constructor(ReviewRes.class,
                        review.id,
                        review.reviewTitle,
                        review.reviewLink,
                        review.bloggerName,
                        review.reviewDescription,
                        review.reviewDate.substring(0, 4)
                                .concat(".")
                                .concat(review.reviewDate.substring(4, 6))
                                .concat(".")
                                .concat(review.reviewDate.substring(6, 8)),
                        review.thumbnailUrl
                ))
                .from(review)
                .where(review.templeName.eq(templeName))
                .orderBy(review.reviewDate.desc())
                .offset((long) (page - 1) * pageSize)
                .limit(pageSize)
                .fetch();

        int totalPages = (int) Math.ceil((double) total / pageSize);

        return new ReviewPageRes(
                templeName,
                total,
                page,
                pageSize,
                totalPages,
                reviews
        );
    }
}
