package sopt.jeolloga.domain.wishlist.core.querydsl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import sopt.jeolloga.domain.wishlist.api.dto.WishlistRes;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class WishlistCustomRepositoryImpl implements WishlistCustomRepository {

    @PersistenceContext
    private final EntityManager em;

    @Override
    public List<WishlistRes> findWishlistContent(Long userId, int offset, int limit) {
        String sql = """
            SELECT 
                t.id AS templestayId,
                t.temple_name AS templeName,
                t.organized_name AS templestayName,
                f.region,
                f.type,
                i.img_url,
                TRUE AS wish
            FROM wishlist w
            JOIN templestay t ON t.id = w.templestay_id
            JOIN filter f ON f.templestay_id = t.id
            LEFT JOIN (
                SELECT templestay_id, MIN(id) AS min_id
                FROM image
                GROUP BY templestay_id
            ) fi ON fi.templestay_id = t.id
            LEFT JOIN image i ON i.id = fi.min_id
            WHERE w.member_id = :userId
            ORDER BY w.id DESC
            LIMIT :limit OFFSET :offset
        """;

        Query query = em.createNativeQuery(sql)
                .setParameter("userId", userId)
                .setParameter("limit", limit)
                .setParameter("offset", offset);

        @SuppressWarnings("unchecked")
        List<Object[]> resultList = query.getResultList();

        return resultList.stream().map(row ->
                new WishlistRes(
                        ((Number) row[0]).longValue(),
                        (String) row[1],
                        (String) row[2],
                        (String) row[3],
                        (String) row[4],
                        (String) row[5],
                        true
                )
        ).toList();
    }

    @Override
    public long countWishlist(Long userId) {
        String countSql = """
            SELECT COUNT(*)
            FROM wishlist
            WHERE member_id = :userId
        """;
        Number result = (Number) em.createNativeQuery(countSql)
                .setParameter("userId", userId)
                .getSingleResult();

        return result.longValue();
    }
}
