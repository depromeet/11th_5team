package depromeet.ohgzoo.iam.category;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CategoryApi {

    private final CategoryService categoryService;

    @GetMapping("/api/v1/category")
    public CategoryResponse category() {
        return categoryService.category();
    }

}