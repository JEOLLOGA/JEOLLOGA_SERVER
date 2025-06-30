package sopt.jeolloga.domain.wishlist;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import sopt.jeolloga.domain.member.Member;
import sopt.jeolloga.domain.templestay.Templestay;

@Entity
@Getter
@Table(name = "wishlist")
public class Wishlist {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "templestay_id")
    private Templestay templestay;

    protected Wishlist() {

    }

    @Builder
    public Wishlist(Member member, Templestay templestay) {
        this.member = member;
        this.templestay = templestay;
    }
}
