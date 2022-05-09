package depromeet.ohgzoo.iam.category;

import java.util.List;

public interface CategoryService {

    CategoryResponse categoryList();

    List<Category> firstCategoryList();

    List<Category> secondCategoryList();
}