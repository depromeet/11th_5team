package depromeet.ohgzoo.iam.member;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    private String profileImg;
    private String nickname;
    private String email;

    @Builder
    public Member(Long id, String profileImg, String nickname, String email) {
        this.id = id;
        this.profileImg = profileImg;
        this.nickname = nickname;
        this.email = email;
    }

    public Member(String profileImg, String nickname, String email) {
        this(null, profileImg, nickname, email);
    }
}
