package sopt.jeolloga.domain.templestay.core.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import sopt.jeolloga.domain.templestay.QTemplestay;
import sopt.jeolloga.domain.templestay.Templestay;
import sopt.jeolloga.domain.filter.QFilter;

import java.util.List;

@RequiredArgsConstructor
public class TemplestayRepositoryImpl implements TemplestayCustomRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<Templestay> searchByFilters(Integer regionMask, Integer typeMask, Integer activityMask, Integer etcMask) {
        QTemplestay t = QTemplestay.templestay;
        QFilter f = QFilter.filter;

        BooleanBuilder builder = buildFilterConditions(f, regionMask, typeMask, activityMask, etcMask);

        // 조인해서 필터 조건 적용
        return queryFactory.selectDistinct(t)
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

        return queryFactory.selectDistinct(t.count())
                .from(t)
                .join(t.filter, f)
                .where(builder)
                .fetchOne();
    }

    private BooleanBuilder buildFilterConditions(QFilter f, Integer regionMask, Integer typeMask, Integer activityMask, Integer etcMask) {
        BooleanBuilder builder = new BooleanBuilder();

        if (regionMask != null && regionMask != 0) {
            builder.and(
                    Expressions.numberTemplate(Integer.class, "{0} & {1}", f.region, regionMask).ne(0)
            );
        }
        if (typeMask != null && typeMask != 0) {
            builder.and(
                    Expressions.numberTemplate(Integer.class, "{0} & {1}", f.type, typeMask).ne(0)
            );
        }
        if (activityMask != null && activityMask != 0) {
            builder.and(
                    Expressions.numberTemplate(Integer.class, "{0} & {1}", f.activity, activityMask).ne(0)
            );
        }
        if (etcMask != null && etcMask != 0) {
            builder.and(
                    Expressions.numberTemplate(Integer.class, "{0} & {1}", f.ect, etcMask).ne(0)
            );
        }

        return builder;
    }
}
