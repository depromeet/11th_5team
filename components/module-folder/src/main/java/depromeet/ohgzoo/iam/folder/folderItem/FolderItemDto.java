package depromeet.ohgzoo.iam.folder.folderItem;

import com.fasterxml.jackson.annotation.JsonFormat;
import depromeet.ohgzoo.iam.category.FirstCategory;
import depromeet.ohgzoo.iam.category.SecondCategory;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
public class FolderItemDto {
    private String postId;
    private FirstCategory firstCategory;
    private SecondCategory secondCategory;
    private List<String> tags;
    private String content;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;
    private int views;

    public String getFirstCategoryName() {
        return firstCategory.getDescription();
    }

    public String getSecondCategoryName() {
        return secondCategory.getDescription();
    }

    public static FolderItemDto of(FolderItem folderItem) {
        return builder()
                .postId(folderItem.getPostId())
                .firstCategory(folderItem.getFirstCategory())
                .secondCategory(folderItem.getSecondCategory())
                .tags(folderItem.getTags())
                .content(folderItem.getContent())
                .createdAt(folderItem.getCreatedAt())
                .views(folderItem.getViews())
                .build();
    }
}
