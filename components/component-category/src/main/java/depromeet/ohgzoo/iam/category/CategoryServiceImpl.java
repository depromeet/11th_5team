package depromeet.ohgzoo.iam.category;

import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    private static final CategoryResponse categoryResponse = new CategoryResponse();
    private static final List<Category> firstCategoryList = new ArrayList<>();
    private static final List<Category> secondCategoryList = new ArrayList<>();

    @Override
    public CategoryResponse categoryList() {
        return categoryResponse;
    }

    @Override
    public List<Category> firstCategoryList() {
        return firstCategoryList;
    }

    @Override
    public List<Category> secondCategoryList() {
        return secondCategoryList;
    }

    @PostConstruct
    public void init() {
        for (SecondCategory secondCategory : SecondCategory.values()) {
            Category category = createCategory(secondCategory);

            switch (secondCategory.getType()) {
                case CategoryType.NEGATIVE:
                    firstCategoryList.add(category);
                    secondCategoryList.add(category);
                    categoryResponse.getNegative().add(category);
                    break;
                case CategoryType.POSITIVE:
                    secondCategoryList.add(category);
                    categoryResponse.getPositive().add(category);
                    break;
                default:
                    if (secondCategory.equals(SecondCategory.DONTKNOW)) firstCategoryList.add(category);
                    secondCategoryList.add(category);
                    categoryResponse.getNatural().add(category);
                    break;
            }
        }
    }

    private Category createCategory(SecondCategory secondCategory) {
        return new Category(
                secondCategory.getCategoryId(),
                secondCategory.name(),
                secondCategory.getName(),
                secondCategory.getImage());
    }

}