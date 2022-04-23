package depromeet.ohgzoo.iam.folder;

import jdk.jfr.Timestamp;
import lombok.Builder;
import lombok.Getter;
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
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class FolderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "folder_item_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "folder_id")
    private Folder folder;

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

    public void setFolder(Folder folder) {
        this.folder = folder;
    }

    @Builder
    public FolderItem(Long id, FirstCategory firstCategory, SecondCategory secondCategory, String content, List<String> tags, Boolean disclosure) {
        this.id = id;
        this.firstCategory = firstCategory;
        this.secondCategory = secondCategory;
        this.content = content;
        this.tags = tags;
        this.disclosure = disclosure;
    }

    public FolderItem(FirstCategory firstCategory, SecondCategory secondCategory, String content, List<String> tags, Boolean disclosure) {
        this(null, firstCategory, secondCategory, content, tags, disclosure);
    }
}