package depromeet.ohgzoo.iam.posts;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
public class UpdatePostsRequest {

    private PostsSecondCategory secondCategory;
    private String content;
    private List<String> tags = new ArrayList<>();
    private Boolean disclosure;

    @Builder
    public UpdatePostsRequest(PostsSecondCategory secondCategory, String content, List<String> tags, Boolean disclosure) {
        this.secondCategory = secondCategory;
        this.content = content;
        this.tags.addAll(tags);
        this.disclosure = disclosure;
    }
}