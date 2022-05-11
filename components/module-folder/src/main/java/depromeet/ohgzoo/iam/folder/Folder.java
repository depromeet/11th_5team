package depromeet.ohgzoo.iam.folder;

import depromeet.ohgzoo.iam.BaseEntity;
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
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Folder extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "folder_id")
    private Long id;

    private String name;

    private String coverImg;

    private Long memberId;

    private boolean isDefault;

    @OneToMany(mappedBy = "folder", cascade = CascadeType.REMOVE)
    private List<FolderItem> folderItems = new ArrayList<>();

    @Builder
    public Folder(Long id, String name, String coverImg, Long memberId, boolean isDefault) {
        this.id = id;
        this.name = name;
        this.coverImg = coverImg;
        this.memberId = memberId;
        this.isDefault = isDefault;
    }

    Folder(String name, String coverImg, Long memberId) {
        this(null, name, coverImg, memberId, false);
    }

    Folder(String name, String coverImg, Long memberId, boolean isDefault) {
        this(null, name, coverImg, memberId, isDefault);
    }

    public void updateName(String name) {
        this.name = name;
    }

    public void changeCoverImg(FirstCategory firstCategory) {
        this.coverImg = CoverImageUrl.returnCoverImage(firstCategory);
    }
}
