package depromeet.ohgzoo.iam.folder.folderItem;

import depromeet.ohgzoo.iam.folder.FirstCategory;
import depromeet.ohgzoo.iam.folder.SecondCategory;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.UniqueElements;

import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class FolderItemCreateRequest {
    @NotNull
    private Long postId;
    @NotNull
    private FirstCategory firstCategory;
    @NotNull
    private SecondCategory secondCategory;
    @NotNull
    private String content;
    @NotNull
   private List<String> tags;
    @NotNull
    private Boolean disclosure;

    public FolderItemCreateRequest(FirstCategory firstCategory, SecondCategory secondCategory, String content, List<String> tags, Boolean disclosure) {
        this.firstCategory = firstCategory;
        this.secondCategory = secondCategory;
        this.content = content;
        this.tags = tags;
        this.disclosure = disclosure;
    }
}
