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

    private int totalCount;
    private String categoryName;

    public CategoryItemsResponse(int totalCount, String categoryName, List<CategoryItemDTO> posts) {
        this.totalCount = totalCount;
        this.categoryName = categoryName;
        this.posts.addAll(posts);
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

        public String getFirstCategoryName() {
            return firstCategory.getDescription();
        }

        public String getSecondCategoryName() {
            return secondCategory.getDescription();
        }
    }
}