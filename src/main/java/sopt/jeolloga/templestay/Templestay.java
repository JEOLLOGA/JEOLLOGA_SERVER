package sopt.jeolloga.templestay;

import jakarta.persistence.*;
import lombok.Getter;
import sopt.jeolloga.filter.Filter;
import sopt.jeolloga.image.Image;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Table(name = "templestay")
public class Templestay {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String url;

    @Column
    private String templestayName;

    @Column
    private String organizedName;

    @Column
    private String phone;

    @Column
    private String introduction;

    @Column
    private String address;

    @Column
    private String templeName;

    @Column
    private String schedule;

    @Column
    private LocalDateTime updatedAt;

    @Column
    private Long view;

    @OneToMany(mappedBy = "templestay", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Filter> filter;

    @OneToMany(mappedBy = "templestay", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Image> images = new ArrayList<>();

    protected Templestay() {

    }

    public Templestay(String url, String templestayName, String organizedName, String phone, String introduction, String address, String templeName, String schedule, LocalDateTime updatedAt, Long view, List<Filter> filter, List<Image> images) {
        this.url = url;
        this.templestayName = templestayName;
        this.organizedName = organizedName;
        this.phone = phone;
        this.introduction = introduction;
        this.address = address;
        this.templeName = templeName;
        this.schedule = schedule;
        this.updatedAt = updatedAt;
        this.view = view;
        this.filter = filter;
        this.images = images;
    }
}
