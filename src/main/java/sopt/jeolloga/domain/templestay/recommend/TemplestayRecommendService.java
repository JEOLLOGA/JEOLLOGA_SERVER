package sopt.jeolloga.domain.templestay.recommend;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sopt.jeolloga.common.type.MemberType;
import sopt.jeolloga.domain.member.Member;
import sopt.jeolloga.domain.member.core.MemberRepository;
import sopt.jeolloga.domain.templestay.core.repository.TemplestayRepository;
import sopt.jeolloga.exception.BusinessErrorCode;
import sopt.jeolloga.exception.BusinessException;

@Service
@RequiredArgsConstructor
public class TemplestayRecommendService {

    private final TemplestayRepository templestayRepository;
    private final MemberRepository memberRepository;

    @Transactional(readOnly = true)
    @Cacheable(value = "recommend", key = "'type:' + #type.name() + ':limit:' + #limit")
    public List<TemplestayPickRes> recommendByType(MemberType type, int limit) {
        RecommendProfile p = MemberTypeProfiles.of(type);
        return templestayRepository.findTopByMasks(
                p.typeMask(),
                p.activityMask(),
                null,
                null,
                limit
        );
    }

    @Transactional(readOnly = true)
    public List<TemplestayPickRes> recommendForUser(Long userId, int limit) {
        MemberType type = memberRepository.findById(userId)
                .map(Member::getType)
                .orElseThrow(() -> new BusinessException(BusinessErrorCode.NOT_FOUND_USER));

        if (type == null) {
            throw new BusinessException(BusinessErrorCode.INVALID_REQUEST);
        }

        RecommendProfile p = MemberTypeProfiles.of(type);

        return templestayRepository.findTopByMasks(
                p.typeMask(),
                p.activityMask(),
                null,
                null,
                limit
        );
    }

    @Transactional(readOnly = true)
    @Cacheable(value = "recommendAllFlat", key = "'limit:' + #limit")
    public List<TemplestayPickRes> recommendFlat(int limit) {
        List<TemplestayPickRes> collected = new java.util.ArrayList<>();
        for (MemberType mt : MemberType.values()) {
            int typeMask = MemberTypeProfiles.of(mt).typeMask();
            collected.addAll(templestayRepository.findRandomByType(typeMask, limit));
        }
        java.util.LinkedHashMap<Long, TemplestayPickRes> dedup = new java.util.LinkedHashMap<>();
        for (TemplestayPickRes r : collected) {
            dedup.putIfAbsent(r.templestayId(), r);
        }
        return dedup.values().stream().limit(limit).toList();
    }

}
