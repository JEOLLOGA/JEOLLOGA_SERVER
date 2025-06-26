package sopt.jeolloga.util;

import sopt.jeolloga.common.filter.BitMask;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

public class FilterMaskUtil {

    public static int combineMasks(Set<? extends BitMask> filters) {
        if (filters == null || filters.isEmpty()) return 0;
        return filters.stream()
                .mapToInt(BitMask::getMask)
                .reduce(0, (a, b) -> a | b);
    }

    public static <E extends Enum<E> & BitMask> String decodeMask(int mask, Class<E> enumClass) {
        return Arrays.stream(enumClass.getEnumConstants())
                .filter(e -> (mask & e.getMask()) != 0)
                .map(BitMask::getLabel)
                .collect(Collectors.joining(","));
    }
}
