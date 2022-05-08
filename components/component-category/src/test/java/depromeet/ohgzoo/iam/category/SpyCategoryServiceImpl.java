package depromeet.ohgzoo.iam.category;

import java.util.ArrayList;
import java.util.List;

public class SpyCategoryServiceImpl implements CategoryService {

    public CategoryResponse categoryResponse = new CategoryResponse();
    public List<Category> firstCategory = new ArrayList<>();
    public List<Category> secondCategory = new ArrayList<>();

    @Override
    public CategoryResponse categoryList() {
        categoryResponse.getNegative().add(new Category(1, "ne1", "ne1", "image1"));
        categoryResponse.getPositive().add(new Category(2, "p1", "p1", "image2"));
        categoryResponse.getNatural().add(new Category(3, "na1", "na1", "image3"));

        return categoryResponse;
    }

    @Override
    public List<Category> firstCategoryList() {
        firstCategory.add(new Category(1, "first", "first", "image1"));

        return firstCategory;
    }

    @Override
    public List<Category> secondCategoryList() {
        secondCategory.add(new Category(2, "second", "second", "image2"));

        return secondCategory;
    }
}
