package depromeet.ohgzoo.iam.folder.folderItem;

import depromeet.ohgzoo.iam.BaseEntity;
import depromeet.ohgzoo.iam.DecryptConverter;
import depromeet.ohgzoo.iam.ListToStringConverter;
import depromeet.ohgzoo.iam.category.FirstCategory;
import depromeet.ohgzoo.iam.category.SecondCategory;
import depromeet.ohgzoo.iam.folder.Folder;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FolderItem extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "folder_item_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    private FirstCategory firstCategory;

    @Enumerated(EnumType.STRING)
    private SecondCategory secondCategory;
    @Convert(converter = DecryptConverter.class)
    private String content;

    @Convert(converter = ListToStringConverter.class)
    private List<String> tags;

    private Boolean disclosure;

    private int views;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "folder_id")
    private Folder folder;

    @Column(name = "post_id", unique = true)
    private String postId;

    private Long memberId;

    @Builder
    public FolderItem(Long id, FirstCategory firstCategory, SecondCategory secondCategory, String content, List<String> tags, Boolean disclosure, int views, String postId, Long memberId) {
        this.id = id;
        this.firstCategory = firstCategory;
        this.secondCategory = secondCategory;
        this.content = content;
        this.tags = tags;
        this.disclosure = disclosure;
        this.views = views;
        this.postId = postId;
        this.memberId = memberId;
    }

    public FolderItem(FirstCategory firstCategory, SecondCategory secondCategory, String content, List<String> tags, Boolean disclosure, String postId, Long memberId) {
        this(null, firstCategory, secondCategory, content, tags, disclosure, 0, postId, memberId);
    }

    public void setFolder(Folder folder) {
        this.folder = folder;
        folder.getFolderItems().add(this);
    }

    public void changeFolder(Folder oldFolder, Folder newFolder) {
        this.folder = newFolder;
        oldFolder.getFolderItems().remove(this);
        newFolder.getFolderItems().add(this);
    }

    public void updateFolderItem(FolderItemUpdateRequest request) {
        this.secondCategory = request.getSecondCategory();
        this.content = request.getContent();
        this.tags = request.getTags();
        this.disclosure = request.getDisclosure();
    }

    public void increaseViews() {
        views++;
    }

    public void updateContent(String encrypt) {
        this.content = encrypt;
    }
}