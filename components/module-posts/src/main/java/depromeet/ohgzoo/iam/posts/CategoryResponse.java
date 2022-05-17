package depromeet.ohgzoo.iam.posts;

import depromeet.ohgzoo.iam.category.SecondCategory;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Getter
@NoArgsConstructor
public class CategoryResponse {

    int count;
    Integer categoryId;
    String description;
    String image;

    public CategoryResponse(int count, SecondCategory secondCategory) {
        this.count = count;
        this.categoryId = secondCategory.getCategoryId();
        this.description = secondCategory.getDescription();
        this.image = secondCategory.getImage();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CategoryResponse that = (CategoryResponse) o;
        return Objects.equals(count, that.count) && Objects.equals(categoryId, that.categoryId) && Objects.equals(description, that.description) && Objects.equals(image, that.image);
    }

    @Override
    public int hashCode() {
        return Objects.hash(count, categoryId, description, image);
    }
}