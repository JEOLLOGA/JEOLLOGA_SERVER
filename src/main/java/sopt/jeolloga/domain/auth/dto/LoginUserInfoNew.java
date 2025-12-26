package sopt.jeolloga.domain.auth.dto;

import sopt.jeolloga.common.type.MemberType;

public record LoginUserInfoNew(
        Long userId,
        String nickname,
        boolean userInfo,
        boolean hasType,
        MemberType type
) {
}
