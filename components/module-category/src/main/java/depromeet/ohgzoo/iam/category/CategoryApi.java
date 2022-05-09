package depromeet.ohgzoo.iam.category;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
        return categoryService.firstCategoryList();
    }

    @GetMapping("/api/v1/secondcategory")
    public List<Category> secondCategoryList() {
        return categoryService.secondCategoryList();
    }

}