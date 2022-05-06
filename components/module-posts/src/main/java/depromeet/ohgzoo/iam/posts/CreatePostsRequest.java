package depromeet.ohgzoo.iam.posts;

import depromeet.ohgzoo.iam.category.FirstCategory;
import depromeet.ohgzoo.iam.category.SecondCategory;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CreatePostsRequest {
    private String postId;
    private FirstCategory firstCategory;
    private SecondCategory secondCategory;
    private String content;
    private List<String> tags = new ArrayList<>();
    private boolean disclosure;

    @Builder
    public CreatePostsRequest(String postId, FirstCategory firstCategory, SecondCategory secondCategory, String content, List<String> tags, boolean disclosure) {
        this.postId = postId;
        this.firstCategory = firstCategory;
        this.secondCategory = secondCategory;
        this.content = content;
        this.tags = tags;
        this.disclosure = disclosure;
    }
}
