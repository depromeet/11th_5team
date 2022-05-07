package depromeet.ohgzoo.iam.category;

import depromeet.ohgzoo.iam.category.CategoryResponse.Category;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Arrays;

@Service
public class CategoryServiceImpl implements CategoryService {

    private static final CategoryResponse categoryResponse = new CategoryResponse();

    @Override
    public CategoryResponse category() {
        return categoryResponse;
    }

    @PostConstruct
    public void init() {
        initNegative();
        initPositive();
    }

    private void initNegative() {
        FirstCategory[] firstCategories = FirstCategory.values();
        Arrays.stream(firstCategories).forEach(firstCategory -> categoryResponse.getNegative().add(
                createCategory(firstCategory.getCategoryId(), firstCategory.name(), firstCategory.getName(), firstCategory.getImage())));
    }

    private void initPositive() {
        SecondCategory[] secondCategories = SecondCategory.values();
        Arrays.stream(secondCategories).forEach(secondCategory -> categoryResponse.getPositive().add(
                createCategory(secondCategory.getCategoryId(), secondCategory.name(), secondCategory.getName(), secondCategory.getImage())));
    }

    private Category createCategory(Integer firstCategory, String firstCategory1, String firstCategory2, String firstCategory3) {
        return new Category(firstCategory, firstCategory1, firstCategory2, firstCategory3);
    }
}