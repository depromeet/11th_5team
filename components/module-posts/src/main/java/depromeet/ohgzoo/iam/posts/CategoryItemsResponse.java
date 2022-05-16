package depromeet.ohgzoo.iam.posts;

import com.fasterxml.jackson.annotation.JsonFormat;
import depromeet.ohgzoo.iam.category.FirstCategory;
import depromeet.ohgzoo.iam.category.SecondCategory;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Getter
@NoArgsConstructor
public class CategoryItemsResponse {

    private int totalCount;
    private List<CategoryItemDTO> posts;

    public CategoryItemsResponse(int totalCount, List<CategoryItemDTO> posts) {
        this.totalCount = totalCount;
        this.posts = posts;
    }

    @Getter
    @Builder
    static class CategoryItemDTO {
        private String postId;
        private FirstCategory firstCategory;
        private SecondCategory secondCategory;
        private List<String> tags;
        private String content;
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        private LocalDateTime createdDate;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            CategoryItemDTO that = (CategoryItemDTO) o;
            return Objects.equals(postId, that.postId) && firstCategory == that.firstCategory && secondCategory == that.secondCategory && Objects.equals(tags, that.tags) && Objects.equals(content, that.content) && Objects.equals(createdDate, that.createdDate);
        }

        @Override
        public int hashCode() {
            return Objects.hash(postId, firstCategory, secondCategory, tags, content, createdDate);
        }
    }
}