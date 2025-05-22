package sopt.jeolloga.common.filter;

public enum Activity implements BitMask {
    _108배(0),
    스님과의차담(1),
    새벽예불(2),
    염주만들기(3),
    연등만들기(4),
    명상(5);

    private final int bit;
    Activity(int bit) { this.bit = bit; }

    @Override public int getBit() { return bit; }
    @Override public String getLabel() { return name(); }
}