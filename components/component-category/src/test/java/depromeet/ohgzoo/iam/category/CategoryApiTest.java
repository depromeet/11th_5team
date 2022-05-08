package depromeet.ohgzoo.iam.category;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class CategoryApiTest {

    MockMvc mockMvc;
    SpyCategoryServiceImpl spyCategoryService;

    @BeforeEach
    public void setUp() {
        this.spyCategoryService = new SpyCategoryServiceImpl();
        this.mockMvc = MockMvcBuilders.standaloneSetup(new CategoryApi(spyCategoryService)).build();
    }

    @Test
    public void status_isOk() throws Exception {
        mockMvc.perform(get("/api/v1/category"))
                .andExpect(status().isOk());
    }

    @Test
    public void returnsCategoryResponse() throws Exception {
        mockMvc.perform(get("/api/v1/category"))
                .andExpect(status().isOk());

        assertThat(spyCategoryService.categoryResponse.getNegative().size()).isEqualTo(1);
        assertThat(spyCategoryService.categoryResponse.getNegative().get(0).getCardgoryName()).isEqualTo("ne1");
        assertThat(spyCategoryService.categoryResponse.getPositive().size()).isEqualTo(1);
        assertThat(spyCategoryService.categoryResponse.getPositive().get(0).getCardgoryName()).isEqualTo("p1");
        assertThat(spyCategoryService.categoryResponse.getNatural().size()).isEqualTo(1);
        assertThat(spyCategoryService.categoryResponse.getNatural().get(0).getCardgoryName()).isEqualTo("na1");
    }
}