package depromeet.ohgzoo.iam.posts;

import com.fasterxml.jackson.annotation.JsonFormat;
import depromeet.ohgzoo.iam.category.FirstCategory;
import depromeet.ohgzoo.iam.category.SecondCategory;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@EqualsAndHashCode
public class PostsDto {
    private String id;
    private FirstCategory firstCategory;
    private SecondCategory secondCategory;
    private String content;
    private List<String> tags;
    private boolean disclosure;
    private int views;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;

    @Builder
    public PostsDto(String id, FirstCategory firstCategory, SecondCategory secondCategory, String content, List<String> tags, boolean disclosure, int views, LocalDateTime createdAt) {
        this.id = id;
        this.firstCategory = firstCategory;
        this.secondCategory = secondCategory;
        this.content = content;
        this.tags = tags;
        this.disclosure = disclosure;
        this.views = views;
        this.createdAt = createdAt;
    }

    public PostsDto(Posts posts) {
        this(
                posts.getId(),
                posts.getFirstCategory(),
                posts.getSecondCategory(),
                posts.getContent(),
                posts.getTags(),
                posts.isDisclosure(),
                posts.getViews(),
                posts.getCreatedAt());
    }
}
