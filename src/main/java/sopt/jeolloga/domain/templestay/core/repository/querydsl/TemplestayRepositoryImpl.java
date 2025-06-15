package sopt.jeolloga.domain.templestay.core.repository.querydsl;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.NumberExpression;
import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import sopt.jeolloga.domain.templestay.QTemplestay;
import sopt.jeolloga.domain.templestay.Templestay;
import sopt.jeolloga.domain.filter.QFilter;
import sopt.jeolloga.domain.templestay.api.dto.TemplestayDetailsRes;
import sopt.jeolloga.domain.templestay.core.repository.querydsl.TemplestayCustomRepository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class TemplestayRepositoryImpl implements TemplestayCustomRepository {

    private final JPAQueryFactory queryFactory;

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
                .select(t.count()) // distinct 제거하여 count 최적화
                .from(t)
                .join(t.filter, f)
                .where(builder)
                .fetchOne();
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
        return (mask != null && mask != 0)
                ? Expressions.numberTemplate(Integer.class, "{0} & {1}", field, mask).ne(0)
                : null;
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
}