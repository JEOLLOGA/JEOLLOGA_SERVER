package sopt.jeolloga.util;

import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.Expressions;
import sopt.jeolloga.domain.filter.QFilter;
import sopt.jeolloga.domain.templestay.QTemplestay;

public class SortUtils {

    public static OrderSpecifier<?> getTemplestaySort(String sort, QTemplestay t, QFilter f) {
        if (sort == null || sort.isBlank()) {
            return t.id.desc();
        }

        // 추천순 = 걍 랜덤, 찜 많은 순 = templestay에서 view가 제일 높을거, 가격 낮은순=filter테이블의 가격이 낮은순으로 tempelstay테이블에서 찾기
        return switch (sort.toLowerCase()) {
            case "price_asc" -> f.price.asc();
            case "name_desc" -> t.templestayName.desc();
            default -> t.id.desc();
        };
    }
}