package sopt.jeolloga.domain.templestay.recommend;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sopt.jeolloga.common.type.MemberType;
import sopt.jeolloga.domain.templestay.core.repository.TemplestayRepository;

@Service
@RequiredArgsConstructor
public class TemplestayRecommendService {

    private final TemplestayRepository templestayRepository;

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
