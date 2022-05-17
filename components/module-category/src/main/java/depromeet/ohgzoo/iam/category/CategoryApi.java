package depromeet.ohgzoo.iam.category;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class CategoryApi {

    private final CategoryService categoryService;

    @GetMapping("/api/v1/category")
    public CategoryResponse categoryList() {
        return categoryService.categoryList();
    }

    @GetMapping("/api/v1/firstcategory")
    public List<Category> firstCategoryList() {
        return categoryService.firstCategoryList()
                .stream()
                .map(category -> mapToCategory(category.getCategoryId(), category.name(), category.getDescription(), category.getImage()))
                .collect(Collectors.toList());
    }

    @GetMapping("/api/v1/secondcategory")
    public List<Category> secondCategoryList() {
        return categoryService.secondCategoryList()
                .stream()
                .map(category -> mapToCategory(category.getCategoryId(), category.name(), category.getDescription(), category.getImage()))
                .collect(Collectors.toList());
    }

    private Category mapToCategory(Integer id, String name, String description, String image) {
        return new Category(id, name, description, image);
    }

}