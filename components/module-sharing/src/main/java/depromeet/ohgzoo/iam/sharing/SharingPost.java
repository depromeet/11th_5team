package depromeet.ohgzoo.iam.sharing;

import depromeet.ohgzoo.iam.BaseEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SharingPost extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String receiverName;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING) // 리팩토링 필요
    private SharingCategory sharingCategory;

    @Column(nullable = false)
    private String postId;

    @Column(nullable = false)
    private Long memberId;

    @Builder
    public SharingPost(String receiverName, SharingCategory sharingCategory, String postId, Long memberId) {
        this.receiverName = receiverName;
        this.sharingCategory = sharingCategory;
        this.postId = postId;
        this.memberId = memberId;
    }
}
