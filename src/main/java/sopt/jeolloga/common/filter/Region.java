package sopt.jeolloga.common.filter;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum Region implements BitMask {
    강원(1),
    경기(2),
    경남(3),
    경북(4),
    광주(5),
    대구(6),
    대전(7),
    부산(8),
    서울(9),
    인천(10),
    전남(11),
    전북(12),
    제주(13),
    충남(14),
    충북(15),
    울산(16),
    세종(17);

    private final int bit;

    Region(int bit) { this.bit = bit; }

    @Override
    public int getBit() {
        return bit;
    }

    @Override
    public String getLabel() {
        return name();
    }

    public static List<Region> fromMask(int mask) {
        return Arrays.stream(values())
                .filter(region -> (region.getMask() & mask) != 0)
                .collect(Collectors.toList());
    }
}