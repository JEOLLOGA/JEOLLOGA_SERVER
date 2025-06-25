package sopt.jeolloga.domain.member.api.dto.req;

import jakarta.validation.constraints.NotBlank;

public record MemberOnboardingReq(
        @NotBlank(message = "나이대는 필수입니다.") String ageRange,
        @NotBlank(message = "성별은 필수입니다.") String gender,
        @NotBlank(message = "종교 정보는 필수입니다.") String religion,
        @NotBlank(message = "템플스테이 경험 여부는 필수입니다.") boolean hasExperience
) {
}
