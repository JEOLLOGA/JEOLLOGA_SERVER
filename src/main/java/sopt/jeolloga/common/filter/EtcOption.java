package sopt.jeolloga.common.filter;

public enum EtcOption implements BitMask {
    주차가능(0),
    _1인실(1),
    단체가능(2);

    private final int bit;
    EtcOption(int bit) { this.bit = bit; }

    @Override public int getBit() { return bit; }
    @Override public String getLabel() { return name(); }
}