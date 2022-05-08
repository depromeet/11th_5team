package depromeet.ohgzoo.iam.category;

import depromeet.ohgzoo.iam.IntegrationTest;
import org.junit.jupiter.api.Test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class CategoryApiIntegrationTest extends IntegrationTest {

    @Test
    public void categoryList() throws Exception {
        mockMvc.perform(get("/api/v1/category"))
                .andExpect(status().isOk());
    }

    @Test
    public void firstCategoryList() throws Exception {
        mockMvc.perform(get("/api/v1/firstcategory"))
                .andExpect(status().isOk());
    }

    @Test
    public void secondCategoryList() throws Exception {
        mockMvc.perform(get("/api/v1/secondcategory"))
                .andExpect(status().isOk());
    }

}
