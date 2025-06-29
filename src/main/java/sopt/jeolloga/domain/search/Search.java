package sopt.jeolloga.domain.search;

import jakarta.persistence.*;
import lombok.Getter;
import sopt.jeolloga.domain.member.Member;

@Entity
@Getter
@Table(name = "search")
public class Search {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Column
    private String content;

    public Search(Member member, String content) {
        this.member = member;
        this.content = content;
    }

    protected Search() {

    }

    public boolean isOwnedBy(Long userId) {
        return this.member != null && this.member.getId().equals(userId);
    }
}

