package sopt.jeolloga.common.filter;

import java.util.Collection;

public final class BitMasks {
    private BitMasks() {}

    public static int of(BitMask... flags) {
        int mask = 0;
        for (BitMask f : flags) {
            mask |= f.getMask();
        }
        return mask;
    }

    public static int of(Collection<? extends BitMask> flags) {
        int mask = 0;
        for (BitMask f : flags) {
            mask |= f.getMask();
        }
        return mask;
    }

    public static boolean matchesAll(int columnMask, int requiredMask) {
        return (columnMask & requiredMask) == requiredMask;
    }

    public static boolean matchesAny(int columnMask, int anyMask) {
        return (columnMask & anyMask) != 0;
    }
}