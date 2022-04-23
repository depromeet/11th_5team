package depromeet.ohgzoo.iam.folder;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
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

    @OneToMany
    @JoinColumn(name = "folder_item_id")
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
    public void addFolderItem(FolderItem folderItem){
        folderItems.add(folderItem);
    }
    public void changeCoverImg(FirstCategory firstCategory){
      this.coverImg = CoverImageUrl.returnCoverImage(firstCategory);
    }
}
