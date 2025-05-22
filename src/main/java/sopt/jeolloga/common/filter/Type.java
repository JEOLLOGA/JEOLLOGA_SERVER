package sopt.jeolloga.common.filter;

public enum Type implements BitMask {
    당일형(0),
    휴식형(1),
    체험형(2);

    private final int bit;
    Type(int bit) { this.bit = bit; }

    @Override public int getBit() { return bit; }
    @Override public String getLabel() { return name(); }
}