package depromeet.ohgzoo.iam.posts;

import lombok.Getter;

@Getter
public class CreatePostsResult {
    private String postId;

    public CreatePostsResult(String postId) {
        this.postId = postId;
    }
}
