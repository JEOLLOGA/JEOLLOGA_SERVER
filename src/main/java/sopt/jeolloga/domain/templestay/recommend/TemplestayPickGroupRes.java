package sopt.jeolloga.domain.templestay.recommend;

import java.util.List;
import java.util.Map;
import sopt.jeolloga.common.type.MemberType;

public record TemplestayPickGroupRes(
        Map<MemberType, List<TemplestayPickRes>> results
) {}
