package sopt.jeolloga.domain.member.core;

import org.springframework.stereotype.Service;
import sopt.jeolloga.domain.member.Member;
import sopt.jeolloga.domain.member.api.dto.req.MemberOnboardingReq;
import sopt.jeolloga.domain.member.api.dto.res.MemberOnboardingRes;
import sopt.jeolloga.domain.member.core.MemberRepository;
import sopt.jeolloga.exception.BusinessErrorCode;
import sopt.jeolloga.exception.BusinessException;
import sopt.jeolloga.exception.ErrorCode;

@Service
public class MemberService {
    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public void createOnboarding(Long userId, MemberOnboardingReq memberOnboardingReq) {
        Member member = memberRepository.findById(userId)
                .orElseThrow(() -> new BusinessException(BusinessErrorCode.NOT_FOUND_USER));
        member.onboard(
                memberOnboardingReq.ageRange(),
                memberOnboardingReq.gender(),
                memberOnboardingReq.religion(),
                memberOnboardingReq.hasExperience()
        );
    }

    public MemberOnboardingRes getMemberInfo(Long userId) {
        Member member = memberRepository.findById(userId)
                .orElseThrow(() -> new BusinessException(BusinessErrorCode.NOT_FOUND_USER));
        return MemberOnboardingRes.from(member);
    }
}
