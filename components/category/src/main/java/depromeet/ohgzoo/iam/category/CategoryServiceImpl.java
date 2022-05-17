package depromeet.ohgzoo.iam.category;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    private static final CategoryResponse categoryResponse;
    private static final List<Category> firstCategoryList = new ArrayList<>();
    private static final List<Category> secondCategoryList = new ArrayList<>();

    static {
        firstCategoryList.addAll(Arrays.stream(FirstCategory.values())
                .map(category -> new Category(category.getCategoryId(), category.name(), category.getName(), category.getImage()))
                .collect(Collectors.toList()));

        secondCategoryList.addAll(Arrays.stream(SecondCategory.values())
                .filter(category -> !category.getType().equals(CategoryType.NONE))
                .map(category -> new Category(category.getCategoryId(), category.name(), category.getName(), category.getImage()))
                .collect(Collectors.toList()));

        categoryResponse = new CategoryResponse(
                getCategories(CategoryType.POSITIVE),
                getCategories(CategoryType.NEGATIVE),
                getCategories(CategoryType.NATURAL));
    }

    private static List<Category> getCategories(CategoryType categoryType) {
        return Arrays.stream(SecondCategory.values())
                .filter(category -> category.getType().equals(categoryType))
                .map(category -> new Category(category.getCategoryId(), category.name(), category.getName(), category.getImage()))
                .collect(Collectors.toList());
    }

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

}