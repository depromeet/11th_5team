package depromeet.ohgzoo.iam.security;

import depromeet.ohgzoo.iam.member.Member;
import lombok.*;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Long id;

    @OneToOne
    @JoinColumn(name = "member_id")
    private Member member;
}