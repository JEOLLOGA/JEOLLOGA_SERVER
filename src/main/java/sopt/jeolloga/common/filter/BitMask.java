package sopt.jeolloga.common.filter;

public interface BitMask {
    int getBit();
    default int getMask() {
        return 1 << (getBit());
    }
    String getLabel();
}
