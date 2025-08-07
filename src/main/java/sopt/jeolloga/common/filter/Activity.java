package sopt.jeolloga.common.filter;

import sopt.jeolloga.exception.BusinessErrorCode;
import sopt.jeolloga.exception.BusinessException;

public enum Activity implements BitMask {
    _108배(0, "108배"),
    스님과의차담(1, "스님과의 차담"),
    새벽예불(2, "새벽 예불"),
    염주만들기(3, "염주 만들기"),
    연등만들기(4, "연등 만들기"),
    명상(5, "명상");

    private final int bit;
    private final String label;

    Activity(int bit, String label) {
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

    public static Activity fromLabel(String label) {
        for (Activity a : values()) {
            if (a.label.equals(label)) return a;
        }
        throw new BusinessException(BusinessErrorCode.BAD_REQUEST_ENUM);
    }
}
