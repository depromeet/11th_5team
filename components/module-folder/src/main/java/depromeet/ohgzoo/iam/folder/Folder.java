package depromeet.ohgzoo.iam.folder;

import depromeet.ohgzoo.iam.member.Member;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Folder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "folder_id")
    private Long id;

    private String name;

    private String coverImg;

    private Long memberId;

    @Builder
    Folder(String name, String coverImg, Long memberId) {
        this.name = name;
        this.coverImg = coverImg;
        this.memberId = memberId;
    }
}
