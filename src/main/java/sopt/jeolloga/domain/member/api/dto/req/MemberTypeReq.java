package sopt.jeolloga.domain.member.api.dto.req;

import jakarta.validation.constraints.NotBlank;

public record MemberTypeReq(
        @NotBlank(message = "type 코드는 필수입니다")
        String type
) {

}
