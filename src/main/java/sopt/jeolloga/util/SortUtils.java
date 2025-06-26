package sopt.jeolloga.util;

import com.querydsl.core.types.OrderSpecifier;
import sopt.jeolloga.domain.templestay.QTemplestay;

public class SortUtils {

    public static OrderSpecifier<?> getTemplestaySort(String sort, QTemplestay t) {
        return switch (sort) {
            case "priceAsc" -> t.filter.price.min().asc();
            case "priceDesc" -> t.filter.price.max().desc();
            case "nameDesc" -> t.templestayName.desc();
            case "nameAsc" -> t.templestayName.asc();
            default -> t.id.desc();
        };
    }
}
