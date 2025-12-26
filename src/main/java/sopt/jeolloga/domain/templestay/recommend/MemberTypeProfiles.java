package sopt.jeolloga.domain.templestay.recommend;

import sopt.jeolloga.common.filter.Activity;
import sopt.jeolloga.common.filter.BitMasks;
import sopt.jeolloga.common.filter.Type;
import sopt.jeolloga.common.type.MemberType;

public final class MemberTypeProfiles {
    private MemberTypeProfiles() {}

    public static RecommendProfile of(MemberType mt) {
        return switch (mt) {
            case IAJ -> new RecommendProfile(
                    Type.휴식형.getMask(),
                    BitMasks.of(Activity.명상, Activity.스님과의차담, Activity.새벽예불)
            );
            case IAP -> new RecommendProfile(
                    Type.휴식형.getMask(),
                    BitMasks.of(Activity.명상, Activity.스님과의차담, Activity.새벽예불)
            );
            case IHJ -> new RecommendProfile(
                    Type.체험형.getMask(),
                    BitMasks.of(Activity._108배, Activity.염주만들기)
            );
            case IHP -> new RecommendProfile(
                    Type.당일형.getMask(),
                    BitMasks.of(Activity.명상, Activity.새벽예불 /* 산책 미정 */)
            );
            case EAJ -> new RecommendProfile(
                    Type.체험형.getMask(),
                    BitMasks.of(Activity.연등만들기, Activity.염주만들기, Activity.스님과의차담)
            );
            case EAP -> new RecommendProfile(
                    Type.당일형.getMask(),
                    BitMasks.of(Activity.연등만들기, Activity.염주만들기)
            );
            case EHJ -> new RecommendProfile(
                    Type.체험형.getMask(),
                    BitMasks.of(Activity._108배 /* 사찰탐방 미정 */)
            );
            case EHP -> new RecommendProfile(
                    Type.체험형.getMask() | Type.당일형.getMask(),
                    BitMasks.of(Activity.명상, Activity._108배 /* 산책 미정 */)
            );
        };
    }
}
