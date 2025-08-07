package sopt.jeolloga.common.filter;

import sopt.jeolloga.exception.BusinessErrorCode;
import sopt.jeolloga.exception.BusinessException;

public enum EtcOption implements BitMask {
    주차가능(0, "주차 가능"),
    _1인실(1, "1인실"),
    단체가능(2, "단체 가능");

    private final int bit;
    private final String label;

    EtcOption(int bit, String label) {
        this.bit = bit;
        this.label = label;
    }

    @Override
    public int getBit() {
        return bit;
    }

    @Override
    public String getLabel() {
        return label;
    }

    public static EtcOption fromLabel(String label) {
        for (EtcOption e : values()) {
            if (e.label.equals(label)) return e;
        }
        throw new BusinessException(BusinessErrorCode.BAD_REQUEST_ENUM);
    }
}
