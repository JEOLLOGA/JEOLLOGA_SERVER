package sopt.jeolloga.domain.templestay.recommend;

import lombok.Builder;

@Builder
public record RecommendProfile(
        int typeMask,
        int activityMask
) {

}