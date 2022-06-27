package depromeet.ohgzoo.iam.folder.folderItem;

import depromeet.ohgzoo.iam.category.FirstCategory;
import depromeet.ohgzoo.iam.category.SecondCategory;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class FolderItemCreateRequest {
    @NotNull
    private String postId;
    @NotNull
    private FirstCategory firstCategory;
    @NotNull
    private SecondCategory secondCategory;
    @NotNull
    private String content;
    @NotNull
    private List<String> tags = new ArrayList<>();
    @NotNull
    private Boolean disclosure;

    public FolderItemCreateRequest(String postId, FirstCategory firstCategory, SecondCategory secondCategory, String content, List<String> tags, Boolean disclosure) {
        this.postId = postId;
        this.firstCategory = firstCategory;
        this.secondCategory = secondCategory;
        this.content = content;
        Optional.ofNullable(tags).ifPresent(this.tags::addAll);
        this.disclosure = disclosure;
    }
}
