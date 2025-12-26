package sopt.jeolloga.domain.member.core;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sopt.jeolloga.common.type.MemberType;
import sopt.jeolloga.domain.member.Member;
import sopt.jeolloga.domain.member.api.dto.req.MemberOnboardingReq;
import sopt.jeolloga.domain.member.api.dto.res.MemberOnboardingRes;
import sopt.jeolloga.domain.member.api.dto.res.MemberTypeResultRes;
import sopt.jeolloga.domain.member.api.dto.res.TypeResultRes;
import sopt.jeolloga.exception.BusinessErrorCode;
import sopt.jeolloga.exception.BusinessException;

@Service
public class MemberService {
    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Transactional
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

    @Transactional
    public MemberTypeResultRes setType(Long userId, MemberType type) {
        Member member = memberRepository.findById(userId)
                .orElseThrow(() -> new BusinessException(BusinessErrorCode.NOT_FOUND_USER));

        if (type == null) {
            throw new BusinessException(BusinessErrorCode.INVALID_REQUEST);
        }

        member.updateType(type);
        return toTypeRes(member, type);
    }

    public MemberTypeResultRes getType(Long userId) {
        Member member = memberRepository.findById(userId)
                .orElseThrow(() -> new BusinessException(BusinessErrorCode.NOT_FOUND_USER));

        MemberType type = member.getType();
        if (type == null) {
            throw new BusinessException(BusinessErrorCode.INVALID_REQUEST);
        }
        return toTypeRes(member, type);
    }

    private MemberType parseTypeOrThrow(String code) {
        try {
            return MemberType.valueOf(code);
        } catch (IllegalArgumentException e) {
            throw new BusinessException(BusinessErrorCode.INVALID_REQUEST);
        }
    }

    private MemberTypeResultRes toTypeRes(Member member, MemberType type) {
        return new MemberTypeResultRes(
                member.getId(),
                type.name(),
                type.getTagline(),
                type.getDescription(),
                type.getRequirement(),
                type.getBestMate(),
                type.getWorstMate()
        );
    }
}
