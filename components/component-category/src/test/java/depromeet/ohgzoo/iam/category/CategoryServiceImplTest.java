package depromeet.ohgzoo.iam.category;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CategoryServiceImplTest {

    static CategoryServiceImpl categoryService = new CategoryServiceImpl();

    @BeforeAll
    static void setUp() {
        categoryService.init();
    }

    @Test
    public void category_returnsCategoryResponse() throws Exception {
        CategoryResponse response = categoryService.category();

        assertThat(response.getNegative().size()).isEqualTo(6);
        assertThat(response.getPositive().size()).isEqualTo(5);
        assertThat(response.getNatural().size()).isEqualTo(6);
    }

}