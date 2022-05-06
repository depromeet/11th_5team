package depromeet.ohgzoo.iam.posts;

import lombok.Getter;

@Getter
public class CreatePostResult {
    private Long postId;

    public CreatePostResult(Long postId) {
        this.postId = postId;
    }
}
