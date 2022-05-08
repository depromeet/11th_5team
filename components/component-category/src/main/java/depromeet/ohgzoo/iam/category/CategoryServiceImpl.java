package depromeet.ohgzoo.iam.category;

import depromeet.ohgzoo.iam.category.CategoryResponse.Category;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class CategoryServiceImpl implements CategoryService {

    private static final CategoryResponse categoryResponse = new CategoryResponse();

    @Override
    public CategoryResponse category() {
        return categoryResponse;
    }

    @PostConstruct
    public void init() {
        for (SecondCategory secondCategory : SecondCategory.values()) {
            switch (secondCategory.getType()) {
                case CategoryType.NEGATIVE:
                    categoryResponse.getNegative().add(createCategory(secondCategory));
                    break;
                case CategoryType.POSITIVE:
                    categoryResponse.getPositive().add(createCategory(secondCategory));
                    break;
                default:
                    categoryResponse.getNatural().add(createCategory(secondCategory));
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