package sopt.jeolloga.common.filter;

import java.util.Arrays;

public enum Type implements BitMask {
    당일형(0),
    휴식형(1),
    체험형(2);

    private final int bit;
    Type(int bit) { this.bit = bit; }

    @Override public int getBit() { return bit; }
    @Override public String getLabel() { return name(); }

    public static String getSingleTypeLabel(int bit) {
        return Arrays.stream(Type.values())
                .filter(t -> t.getBit() == bit)
                .findFirst()
                .map(Type::getLabel)
                .orElse("UNKNOWN");
    }
}