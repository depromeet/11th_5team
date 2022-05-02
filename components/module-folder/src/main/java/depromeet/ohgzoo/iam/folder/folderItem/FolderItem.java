package depromeet.ohgzoo.iam.folder.folderItem;

import depromeet.ohgzoo.iam.ListToStringConverter;
import depromeet.ohgzoo.iam.category.FirstCategory;
import depromeet.ohgzoo.iam.category.SecondCategory;
import depromeet.ohgzoo.iam.folder.Folder;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

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
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FolderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "folder_item_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    private FirstCategory firstCategory;

    @Enumerated(EnumType.STRING)
    private SecondCategory secondCategory;

    private String content;

    @Convert(converter = ListToStringConverter.class)
    private List<String> tags;

    private Boolean disclosure;

    @CreatedDate
    private LocalDateTime createdAt;
    @LastModifiedDate
    private LocalDateTime updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "folder_id")
    private Folder folder;

    @Column(name = "post_id", unique = true)
    private Long postId;

    private Long memberId;

    @Builder
    public FolderItem(Long id, FirstCategory firstCategory, SecondCategory secondCategory, String content, List<String> tags, Boolean disclosure, Long postId, Long memberId) {
        this.id = id;
        this.firstCategory = firstCategory;
        this.secondCategory = secondCategory;
        this.content = content;
        this.tags = tags;
        this.disclosure = disclosure;
        this.postId = postId;
        this.memberId = memberId;
    }

    public FolderItem(FirstCategory firstCategory, SecondCategory secondCategory, String content, List<String> tags, Boolean disclosure, Long postId, Long memberId) {
        this(null, firstCategory, secondCategory, content, tags, disclosure, postId, memberId);
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
}