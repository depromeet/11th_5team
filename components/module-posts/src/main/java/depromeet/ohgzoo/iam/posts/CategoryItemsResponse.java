package depromeet.ohgzoo.iam.posts;

import com.fasterxml.jackson.annotation.JsonFormat;
import depromeet.ohgzoo.iam.category.FirstCategory;
import depromeet.ohgzoo.iam.category.SecondCategory;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
public class CategoryItemsResponse {

    private List<CategoryItemDTO> posts = new ArrayList<>();

    public CategoryItemsResponse(List<CategoryItemDTO> posts) {
        this.posts.addAll(posts);
    }

    public int getTotalCount() {
        return this.posts.size();
    }

    @Getter
    @Builder
    @EqualsAndHashCode
    static class CategoryItemDTO {
        private String postId;
        private FirstCategory firstCategory;
        private SecondCategory secondCategory;
        private List<String> tags;
        private String content;
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        private LocalDateTime createdAt;
        private int views;
    }
}