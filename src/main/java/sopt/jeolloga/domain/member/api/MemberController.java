package sopt.jeolloga.domain.member.api;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import sopt.jeolloga.common.dto.ApiResponse;
import sopt.jeolloga.domain.auth.jwt.CustomUserDetails;
import sopt.jeolloga.domain.member.api.dto.req.MemberOnboardingReq;
import sopt.jeolloga.domain.member.api.dto.res.MemberOnboardingRes;
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

    @GetMapping("/mypage")
    public ResponseEntity<ApiResponse<?>> getMemberInfo(
            @AuthenticationPrincipal CustomUserDetails userDetails
            ) {
        MemberOnboardingRes memberOnboardingRes = memberService.getMemberInfo(userDetails.getUserId());
        return ResponseEntity.ok(ApiResponse.success(memberOnboardingRes));
    }
}
