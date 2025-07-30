package sopt.jeolloga.domain.review;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

public record TemplestayVO(
        String title,
        String link,
        String description,
        String bloggername,
        String bloggerlink,
        String postdate
) {
    public Review toEntity(String templeName) {
        return Review.of(
                templeName,
                title,
                link,
                truncate(description, 50),
                bloggername,
                postdate
        );
    }

    private static String truncate(String str, int maxLength) {
        return (str != null && str.length() > maxLength) ? str.substring(0, maxLength) : str;
    }
}

