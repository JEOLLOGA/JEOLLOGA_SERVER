package sopt.jeolloga.domain.member.api;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sopt.jeolloga.common.dto.ApiResponse;
import sopt.jeolloga.domain.auth.jwt.CustomUserDetails;
import sopt.jeolloga.domain.member.api.dto.req.MemberOnboardingReq;
import sopt.jeolloga.domain.member.core.MemberService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/user")
public class MemberController {
    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping("/onboarding")
    public ResponseEntity<ApiResponse<?>> createOnboarding(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @Valid @RequestBody MemberOnboardingReq memberOnboardingReq
    ) {
        memberService.createOnboarding(userDetails.getUserId(), memberOnboardingReq);
        return ResponseEntity.ok(ApiResponse.success(null));
    }
}
