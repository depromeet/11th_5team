package depromeet.ohgzoo.iam.posts;

import depromeet.ohgzoo.iam.category.FirstCategory;
import depromeet.ohgzoo.iam.category.SecondCategory;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.List;

@EqualsAndHashCode
@Getter
public class PostResponse {
    private String id;
    private FirstCategory firstCategory;
    private SecondCategory secondCategory;
    private String content;
    private List<String> tags;
    private int views;

    private Long memberId;

    @Builder
    public PostResponse(String id, FirstCategory firstCategory, SecondCategory secondCategory, String content, List<String> tags, int views, Long memberId) {
        this.id = id;
        this.firstCategory = firstCategory;
        this.secondCategory = secondCategory;
        this.content = content;
        this.tags = tags;
        this.views = views;
        this.memberId = memberId;
    }

    public PostResponse(Posts posts) {
        this(posts.getId(),
                posts.getFirstCategory(),
                posts.getSecondCategory(),
                posts.getContent(),
                posts.getTags(),
                posts.getViews(),
                posts.getMemberId());
    }
}
