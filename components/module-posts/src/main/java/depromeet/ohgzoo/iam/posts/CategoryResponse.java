package depromeet.ohgzoo.iam.posts;

import depromeet.ohgzoo.iam.category.SecondCategory;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Getter
@NoArgsConstructor
public class CategoryResponse {

    Integer count;
    Integer categoryId;
    String name;
    String image;

    public CategoryResponse(int count, SecondCategory secondCategory) {
        this.count = count;
        this.categoryId = secondCategory.getCategoryId();
        this.name = secondCategory.getName();
        this.image = secondCategory.getImage();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CategoryResponse that = (CategoryResponse) o;
        return Objects.equals(count, that.count) && Objects.equals(categoryId, that.categoryId) && Objects.equals(name, that.name) && Objects.equals(image, that.image);
    }

    @Override
    public int hashCode() {
        return Objects.hash(count, categoryId, name, image);
    }
}