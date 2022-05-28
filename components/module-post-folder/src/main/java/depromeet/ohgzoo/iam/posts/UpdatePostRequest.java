package depromeet.ohgzoo.iam.posts;

import depromeet.ohgzoo.iam.category.SecondCategory;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
public class UpdatePostRequest {

    private SecondCategory secondCategory;
    private String content;
    private List<String> tags = new ArrayList<>();
    private Boolean disclosure;
    private Long folderId;

    @Builder
    public UpdatePostRequest(SecondCategory secondCategory, String content, List<String> tags, Boolean disclosure, Long folderId) {
        this.secondCategory = secondCategory;
        this.content = content;
        this.tags = tags;
        this.disclosure = disclosure;
        this.folderId = folderId;
    }
}