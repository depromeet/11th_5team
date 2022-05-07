package depromeet.ohgzoo.iam.category;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CategoryServiceImplTest {
    CategoryService categoryService = new CategoryServiceImpl();

    @Test
    @DisplayName("Test name")
    public void testName() throws Exception {
        categoryService.category();
    }

}