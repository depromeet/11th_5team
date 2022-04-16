package depromeet.ohgzoo.iam.posts;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CreatePostsRequest {
    private String name;
    private PostsCategory firstCategory;
    private PostsCategory secondCategory;
    private String content;
    private List<String> tags = new ArrayList<>();
    private boolean disclosure;

    public CreatePostsRequest(String name, PostsCategory firstCategory, PostsCategory secondCategory, String content, List<String> tags, boolean disclosure) {
        this.name = name;
        this.firstCategory = firstCategory;
        this.secondCategory = secondCategory;
        this.content = content;
        this.tags = tags;
        this.disclosure = disclosure;
    }
}
