package depromeet.ohgzoo.iam.folder.folderItem;

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
    private LocalDateTime createdDate;

    public static FolderItemDto of(FolderItem folderItem) {
        return FolderItemDto.builder()
                .postId(folderItem.getPostId())
                .firstCategory(folderItem.getFirstCategory())
                .secondCategory(folderItem.getSecondCategory())
                .tags(folderItem.getTags())
                .content(folderItem.getContent())
                .createdDate(folderItem.getCreatedAt())
                .build();
    }
}
