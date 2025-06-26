package sopt.jeolloga.domain.templestay.core.repository.querydsl;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.NumberExpression;
import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import sopt.jeolloga.domain.filter.QFilter;
import sopt.jeolloga.domain.templestay.QTemplestay;
import sopt.jeolloga.domain.templestay.Templestay;
import sopt.jeolloga.domain.templestay.api.dto.TemplestayDetailsRes;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class TemplestayRepositoryImpl implements TemplestayCustomRepository {

    private final JPAQueryFactory queryFactory;

    @PersistenceContext
    private final EntityManager em;

    @Override
    public List<Object[]> fetchFilteredTemplestays(
            Integer regionMask, Integer typeMask, Integer activityMask, Integer etcMask,
            Integer minPrice, Integer maxPrice, String sort, String search,
            int offset, int limit
    ) {
        if (sort == null || sort.isBlank()) {
            sort = "recommend";
        }

        String orderByClause = switch (sort) {
            case "price" -> " ORDER BY f.price ASC, t.id ASC ";
            case "view" -> " ORDER BY t.view DESC, t.id ASC ";
            case "recommend" -> "";
            default -> "";
        };

        String sql = """
        SELECT 
            t.id AS templestayId,
            t.temple_name AS templeName,
            t.organized_name AS templestayName,
            f.region,
            f.type,
            COALESCE(w.wish_count, 0) AS wishCount
        FROM templestay t
        JOIN filter f ON f.templestay_id = t.id
        LEFT JOIN (
            SELECT templestay_id, COUNT(*) AS wish_count
            FROM wishlist
            GROUP BY templestay_id
        ) w ON w.templestay_id = t.id
        WHERE t.organized_name IS NOT NULL
          AND (:regionMask = 0 OR (f.region & :regionMask) != 0)
          AND (:typeMask = 0 OR (f.type & :typeMask) != 0)
          AND (:activityMask = 0 OR (f.activity & :activityMask) != 0)
          AND (:etcMask = 0 OR (f.etc & :etcMask) != 0)
          AND (:minPrice IS NULL OR f.price >= :minPrice)
          AND (:maxPrice IS NULL OR f.price <= :maxPrice)
          AND (
            :search IS NULL OR :search = ''
            OR (
                LOWER(t.templestay_name) LIKE CONCAT('%', LOWER(:search), '%')
                OR LOWER(t.temple_name) LIKE CONCAT('%', LOWER(:search), '%')
            )
        )
        """ + orderByClause + """
        LIMIT :limit OFFSET :offset
        """;

        Query nativeQuery = em.createNativeQuery(sql)
                .setParameter("regionMask", regionMask != null ? regionMask : 0)
                .setParameter("typeMask", typeMask != null ? typeMask : 0)
                .setParameter("activityMask", activityMask != null ? activityMask : 0)
                .setParameter("etcMask", etcMask != null ? etcMask : 0)
                .setParameter("minPrice", minPrice)
                .setParameter("maxPrice", maxPrice)
                .setParameter("search", search != null ? search : "")
                .setParameter("limit", limit)
                .setParameter("offset", offset);

        @SuppressWarnings("unchecked")
        List<Object[]> resultList = nativeQuery.getResultList();

        if ("recommend".equalsIgnoreCase(sort)) {
            Collections.shuffle(resultList);
        }

        return resultList;
    }

    @Override
    public long countFilteredTemplestays(
            Integer regionMask, Integer typeMask, Integer activityMask, Integer etcMask,
            Integer minPrice, Integer maxPrice, String search
    ) {
        String sql = """
        SELECT COUNT(*)
        FROM templestay t
        JOIN filter f ON f.templestay_id = t.id
        WHERE t.organized_name IS NOT NULL
          AND (:regionMask = 0 OR (f.region & :regionMask) != 0)
          AND (:typeMask = 0 OR (f.type & :typeMask) != 0)
          AND (:activityMask = 0 OR (f.activity & :activityMask) != 0)
          AND (:etcMask = 0 OR (f.etc & :etcMask) != 0)
          AND (:minPrice IS NULL OR f.price >= :minPrice)
          AND (:maxPrice IS NULL OR f.price <= :maxPrice)
          AND (
            :search IS NULL OR :search = ''
            OR (
                LOWER(t.templestay_name) LIKE CONCAT('%', LOWER(:search), '%')
                OR LOWER(t.temple_name) LIKE CONCAT('%', LOWER(:search), '%')
            )
        )
        """;

        Query countQuery = em.createNativeQuery(sql)
                .setParameter("regionMask", regionMask != null ? regionMask : 0)
                .setParameter("typeMask", typeMask != null ? typeMask : 0)
                .setParameter("activityMask", activityMask != null ? activityMask : 0)
                .setParameter("etcMask", etcMask != null ? etcMask : 0)
                .setParameter("minPrice", minPrice)
                .setParameter("maxPrice", maxPrice)
                .setParameter("search", (search != null && !search.isBlank()) ? search : null);

        Number result = (Number) countQuery.getSingleResult();
        return result.longValue();
    }

    @Override
    public List<Templestay> searchByFilters(Integer regionMask, Integer typeMask, Integer activityMask, Integer etcMask) {
        QTemplestay t = QTemplestay.templestay;
        QFilter f = QFilter.filter;

        BooleanBuilder builder = buildFilterConditions(f, regionMask, typeMask, activityMask, etcMask);

        if (!builder.hasValue()) {
            return Collections.emptyList();
        }

        return queryFactory.select(t)
                .from(t)
                .join(t.filter, f)
                .where(builder)
                .fetch();
    }

    @Override
    public long countByFilters(Integer regionMask, Integer typeMask, Integer activityMask, Integer etcMask) {
        QTemplestay t = QTemplestay.templestay;
        QFilter f = QFilter.filter;

        BooleanBuilder builder = buildFilterConditions(f, regionMask, typeMask, activityMask, etcMask);

        if (!builder.hasValue()) {
            return 0L;
        }

        return queryFactory
                .select(t.count())
                .from(t)
                .join(t.filter, f)
                .where(builder)
                .fetchOne();
    }

    @Override
    public Optional<TemplestayDetailsRes> findDetailsById(Long id) {
        QTemplestay t = QTemplestay.templestay;
        QFilter f = QFilter.filter;

        NumberExpression<Integer> minPrice = f.price.min();

        TemplestayDetailsRes result = queryFactory
                .select(Projections.constructor(
                        TemplestayDetailsRes.class,
                        t.id,
                        t.templestayName,
                        t.templeName,
                        t.address,
                        t.phone,
                        t.schedule,
                        minPrice.as("price").intValue(),
                        t.introduction,
                        t.url,
                        t.lat,
                        t.lon
                ))
                .from(t)
                .leftJoin(t.filter, f)
                .where(t.id.eq(id))
                .groupBy(t.id)
                .fetchOne();

        return Optional.ofNullable(result);
    }

    private BooleanBuilder buildFilterConditions(QFilter f, Integer regionMask, Integer typeMask, Integer activityMask, Integer etcMask) {
        BooleanBuilder builder = new BooleanBuilder();
        builder.and(matchBitmask(f.region, regionMask));
        builder.and(matchBitmask(f.type, typeMask));
        builder.and(matchBitmask(f.activity, activityMask));
        builder.and(matchBitmask(f.etc, etcMask));
        return builder;
    }

    private BooleanExpression matchBitmask(NumberPath<Integer> field, Integer mask) {
        if (mask == null || mask == 0) return null;
        return Expressions.booleanTemplate("({0} & {1}) != 0", field, mask);
    }

    @Override
    public Optional<Tuple> findDetailsWithPriceById(Long id) {
        QTemplestay t = QTemplestay.templestay;
        QFilter f = QFilter.filter;

        Tuple result = queryFactory
                .select(t, f.price.min())
                .from(t)
                .leftJoin(t.filter, f)
                .where(t.id.eq(id))
                .groupBy(t.id)
                .fetchOne();

        return Optional.ofNullable(result);
    }
}