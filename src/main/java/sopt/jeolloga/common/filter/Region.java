package sopt.jeolloga.common.filter;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum Region implements BitMask {
    강원(0),
    경기(1),
    경남(2),
    경북(3),
    광주(4),
    대구(5),
    대전(6),
    부산(7),
    서울(8),
    인천(9),
    전남(10),
    전북(11),
    제주(12),
    충남(13),
    충북(14),
    울산(15),
    세종(16);

    private final int bit;

    Region(int bit) { this.bit = bit; }

    @Override
    public int getBit() {
        return 1 << bit;
    }

    @Override
    public String getLabel() {
        return name();
    }

    public static List<Region> fromMask(int mask) {
        return Arrays.stream(values())
                .filter(region -> (region.getBit() & mask) != 0)
                .collect(Collectors.toList());
    }
}