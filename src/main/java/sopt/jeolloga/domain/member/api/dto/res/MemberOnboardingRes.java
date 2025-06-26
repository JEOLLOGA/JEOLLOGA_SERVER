package sopt.jeolloga.domain.member.api.dto.res;

import sopt.jeolloga.domain.member.Member;

public record MemberOnboardingRes(
        String nickname,
        String email,
        String ageRange,
        String gender,
        String religion,
        boolean hasExperience
) {
    public static MemberOnboardingRes from(Member member) {
        return new MemberOnboardingRes(
                member.getNickname(),
                member.getEmail(),
                member.getAgeRange(),
                member.getGender(),
                member.getReligion(),
                member.isHasExperience()
        );
    }
}
