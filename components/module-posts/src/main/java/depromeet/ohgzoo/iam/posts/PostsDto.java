package depromeet.ohgzoo.iam.posts;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class PostsDto {
    private PostsFirstCategory firstCategory;
    private PostsSecondCategory secondCategory;
    private String content;
    private List<String> tags;
    private Boolean disclosure;
    private Integer views;
    private LocalDateTime createdAt;

    @Builder
    public PostsDto(Posts posts) {
        this.firstCategory = posts.getFirstCategory();
        this.secondCategory = posts.getSecondCategory();
        this.content = posts.getContent();
        this.tags = posts.getTags();
        this.disclosure = posts.getDisclosure();
        this.views = posts.getViews();
        this.createdAt = posts.getCreatedAt();
    }
}
