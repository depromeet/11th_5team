package depromeet.ohgzoo.iam.category;

import java.util.List;

public interface CategoryService {

    CategoryResponse categoryList();

    List<FirstCategory> firstCategoryList();

    List<SecondCategory> secondCategoryList();
}