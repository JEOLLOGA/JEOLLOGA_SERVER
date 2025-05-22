package sopt.jeolloga.domain.image;

import jakarta.persistence.*;
import lombok.Getter;
import sopt.jeolloga.domain.templestay.Templestay;

import java.time.LocalDateTime;

@Entity
@Getter
@Table(name = "image")
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "templestay_id", nullable = false)
    private Templestay templestay;

    @Column
    private String imgUrl;

    @Column
    private LocalDateTime createdAt;

    protected Image() {

    }

    public Image(Templestay templestay, String imgUrl, LocalDateTime createdAt) {
        this.templestay = templestay;
        this.imgUrl = imgUrl;
        this.createdAt = createdAt;
    }
}
