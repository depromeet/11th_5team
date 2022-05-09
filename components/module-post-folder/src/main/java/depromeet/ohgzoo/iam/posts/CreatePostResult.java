package depromeet.ohgzoo.iam.posts;

import lombok.Getter;

@Getter
public class CreatePostResult {
    private String postId;

    public CreatePostResult(String postId) {
        this.postId = postId;
    }
}
