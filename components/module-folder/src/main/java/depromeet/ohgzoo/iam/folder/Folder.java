package depromeet.ohgzoo.iam.folder;

import depromeet.ohgzoo.iam.category.FirstCategory;
import depromeet.ohgzoo.iam.folder.folderItem.FolderItem;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

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

    @OneToMany(mappedBy = "folder", cascade = CascadeType.REMOVE)
    private List<FolderItem> folderItems = new ArrayList<>();

    @Builder
    public Folder(Long id, String name, String coverImg, Long memberId) {
        this.id = id;
        this.name = name;
        this.coverImg = coverImg;
        this.memberId = memberId;
    }

    Folder(String name, String coverImg, Long memberId) {
        this(null, name, coverImg, memberId);
    }

    public void updateName(String name) {
        this.name = name;
    }

    public void changeCoverImg(FirstCategory firstCategory) {
        this.coverImg = CoverImageUrl.returnCoverImage(firstCategory);
    }
}
