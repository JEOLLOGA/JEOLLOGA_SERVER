package sopt.jeolloga.domain.member.core;

import org.springframework.stereotype.Service;
import sopt.jeolloga.domain.member.core.MemberRepository;

@Service
public class MemberService {
    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }
}
