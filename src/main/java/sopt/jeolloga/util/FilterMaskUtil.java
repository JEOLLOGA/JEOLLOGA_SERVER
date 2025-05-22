package sopt.jeolloga.util;

import sopt.jeolloga.common.filter.BitMask;

import java.util.Set;

public class FilterMaskUtil {

    public static <T extends BitMask> int combineMasks(Set<T> filters) {
        int mask = 0;
        for (T filter : filters) {
            mask |= filter.getMask();
        }
        return mask;
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
}
