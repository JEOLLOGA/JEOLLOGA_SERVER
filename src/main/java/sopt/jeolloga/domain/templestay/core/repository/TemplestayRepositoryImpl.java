package sopt.jeolloga.domain.templestay.core.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import sopt.jeolloga.domain.templestay.QTemplestay;
import sopt.jeolloga.domain.templestay.Templestay;
import sopt.jeolloga.domain.filter.QFilter;

import java.util.Collections;
import java.util.List;

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
        builder.and(matchBitmask(f.ect, etcMask));

        return builder;
    }

    private BooleanExpression matchBitmask(NumberPath<Integer> field, Integer mask) {
        return (mask != null && mask != 0)
                ? Expressions.numberTemplate(Integer.class, "{0} & {1}", field, mask).ne(0)
                : null;
    }
}