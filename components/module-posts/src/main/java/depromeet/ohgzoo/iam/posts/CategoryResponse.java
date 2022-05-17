package depromeet.ohgzoo.iam.posts;

import depromeet.ohgzoo.iam.category.SecondCategory;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@EqualsAndHashCode
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
}