package sopt.jeolloga.util;

import sopt.jeolloga.common.filter.BitMask;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

public class FilterMaskUtil {

    public static int combineMasks(Set<? extends BitMask> filters) {
        if (filters == null || filters.isEmpty()) return 0;
        return filters.stream()
                .mapToInt(BitMask::getBit)
                .reduce(0, (a, b) -> a | b);
    }

    public static <T extends BitMask> boolean matches(int value, T filter) {
        return (value & filter.getMask()) != 0;
    }

    public static <T extends BitMask> boolean matchesAny(int value, Set<T> filters) {
        for (T filter : filters) {
            if (matches(value, filter)) return true;
        }
        return false;
    }

    public static <T extends BitMask> boolean matchesAll(int value, Set<T> filters) {
        for (T filter : filters) {
            if (!matches(value, filter)) return false;
        }
        return true;
    }

    public static <E extends Enum<E> & BitMask> int toMask(String raw, Class<E> enumClass) {
        if (raw == null || raw.isBlank()) return 0;

        return Arrays.stream(raw.split(","))
                .map(String::trim)
                .map(s -> Enum.valueOf(enumClass, s))
                .mapToInt(BitMask::getMask)
                .reduce(0, (a, b) -> a | b);
    }

    public static <E extends Enum<E> & BitMask> String decodeMask(int mask, Class<E> enumClass) {
        return Arrays.stream(enumClass.getEnumConstants())
                .filter(e -> (mask & e.getBit()) == e.getBit())
                .map(BitMask::getLabel)
                .collect(Collectors.joining(","));
    }
}
