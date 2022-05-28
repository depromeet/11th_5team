package depromeet.ohgzoo.iam.posts;

import depromeet.ohgzoo.iam.category.SecondCategory;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
public class UpdatePostsRequest {
    private SecondCategory secondCategory;
    private String content;
    private List<String> tags = new ArrayList<>();
    private Boolean disclosure;

    public UpdatePostsRequest(SecondCategory secondCategory, String content, List<String> tags, Boolean disclosure) {
        this.secondCategory = secondCategory;
        this.content = content;
        this.tags.addAll(tags);
        this.disclosure = disclosure;
    }
}
