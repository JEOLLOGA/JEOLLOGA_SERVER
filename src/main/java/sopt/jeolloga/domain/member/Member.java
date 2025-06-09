package sopt.jeolloga.domain.member;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
@Table(name = "member")
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String nickname;

    @Column
    private String email;

    @Column
    private String ageRange;

    @Column
    private String gender;

    @Column
    private String religion;

    @Column
    private boolean hasExperience;

    protected Member() {

    }

    public Member(String nickname, String email, String ageRange, String gender, String religion, boolean hasExperience) {
        this.nickname = nickname;
        this.email = email;
        this.ageRange = ageRange;
        this.gender = gender;
        this.religion = religion;
        this.hasExperience = hasExperience;
    }
}
