package depromeet.ohgzoo.iam.category;

import depromeet.ohgzoo.iam.category.CategoryResponse.Category;

public class SpyCategoryServiceImpl implements CategoryService {

    public CategoryResponse categoryResponse = new CategoryResponse();

    @Override
    public CategoryResponse category() {
        categoryResponse.getNegative().add(new Category(1, "ne1", "ne1", "image1"));
        categoryResponse.getPositive().add(new Category(2, "p1", "p1", "image2"));
        categoryResponse.getNatural().add(new Category(3, "na1", "na1", "image3"));

        return categoryResponse;
    }
}
