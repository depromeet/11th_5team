package depromeet.ohgzoo.iam.posts;

import lombok.Getter;

@Getter
public class CreatePostsResult {
    private Long postId;

    public CreatePostsResult(Long postId) {
        this.postId = postId;
    }
}
