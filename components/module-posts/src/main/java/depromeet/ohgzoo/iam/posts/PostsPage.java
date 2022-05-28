package depromeet.ohgzoo.iam.posts;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class PostsPage {
    private long totalCount;
    private List<PostsDto> posts = new ArrayList<>();

    public PostsPage(long totalCount, List<PostsDto> posts) {
        this.totalCount = totalCount;
        this.posts.addAll(posts);
    }
}
