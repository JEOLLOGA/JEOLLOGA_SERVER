package sopt.jeolloga.domain.member.api.dto.res;

import sopt.jeolloga.domain.member.Member;

public record MemberOnboardingRes(
        Long userId,
        String nickname,
        String email,
        String ageRange,
        String gender,
        String religion,
        boolean hasExperience
) {
    public static MemberOnboardingRes from(Member member) {
        return new MemberOnboardingRes(
                member.getId(),
                member.getNickname(),
                member.getEmail(),
                member.getAgeRange(),
                member.getGender(),
                member.getReligion(),
                member.isHasExperience()
        );
    }
}
