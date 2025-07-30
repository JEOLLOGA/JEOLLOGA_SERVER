package sopt.jeolloga.domain.review.api.dto;

import java.util.List;

public record ReviewPageRes(
        String templeName,
        long reviewCount,
        int page,
        int pageSize,
        int totalPages,
        List<ReviewRes> reviews
) {}

