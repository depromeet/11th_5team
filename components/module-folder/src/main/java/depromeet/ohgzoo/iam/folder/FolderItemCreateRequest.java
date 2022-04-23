package depromeet.ohgzoo.iam.folder;

import com.sun.istack.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class FolderItemCreateRequest {
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
