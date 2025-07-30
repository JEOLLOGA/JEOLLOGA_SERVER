package sopt.jeolloga.domain.review.core;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "review")
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String templeName;

    @Column
    private String reviewTitle;

    @Column
    private String reviewDescription;

    @Column
    private String bloggerName;

    @Column
    private String reviewDate;

    @Column
    private String reviewLink;

    @Column
    private String thumbnailUrl;

    public Review(String templeName, String title, String description,
                  String bloggerName, String reviewDate, String reviewLink, String thumbnailUrl) {
        this.templeName = templeName;
        this.reviewTitle = title;
        this.reviewDescription = description;
        this.bloggerName = bloggerName;
        this.reviewDate = reviewDate;
        this.reviewLink = reviewLink;
        this.thumbnailUrl = thumbnailUrl;
    }

    public static Review of(String templeName, String title, String link,
                            String description, String bloggerName, String postdate) {
        return new Review(
                templeName,
                title,
                description,
                bloggerName,
                postdate,
                link,
                null
        );
    }
}
