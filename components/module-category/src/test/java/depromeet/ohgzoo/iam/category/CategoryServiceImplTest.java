package depromeet.ohgzoo.iam.category;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class CategoryServiceImplTest {

    static CategoryServiceImpl categoryService = new CategoryServiceImpl();

    @BeforeAll
    static void setUp() {
        categoryService.init();
    }

    @Test
    public void categoryList_returnsCategoryResponse() throws Exception {
        CategoryResponse response = categoryService.categoryList();

        assertThat(response.getNegative().size()).isEqualTo(6);
        assertThat(response.getPositive().size()).isEqualTo(5);
        assertThat(response.getNatural().size()).isEqualTo(2);
    }

    @Test
    public void firstCategoryList_returnsCategoryResponse() throws Exception {
        List<Category> categories = categoryService.firstCategoryList();

        assertThat(categories.size()).isEqualTo(7);
    }

    @Test
    public void secondCategoryList_returnsCategoryResponse() throws Exception {
        List<Category> categories = categoryService.secondCategoryList();

        assertThat(categories.size()).isEqualTo(13);
    }
}