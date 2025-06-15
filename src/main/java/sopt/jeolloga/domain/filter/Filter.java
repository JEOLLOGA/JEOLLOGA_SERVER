package sopt.jeolloga.domain.filter;

import jakarta.persistence.*;
import lombok.Getter;
import sopt.jeolloga.domain.templestay.Templestay;

@Entity
@Getter
@Table(name = "filter")
public class Filter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "templestay_id", nullable = false)
    private Templestay templestay;

    @Column
    private int region;

    @Column
    private int type;

    @Column
    private int activity;

    @Column
    private int etc;

    @Column
    private int price;

    protected Filter() {

    }

    public Filter(Templestay templestay, int region, int type, int activity, int etc, int price) {
        this.templestay = templestay;
        this.region = region;
        this.type = type;
        this.activity = activity;
        this.etc = etc;
        this.price = price;
    }
}
