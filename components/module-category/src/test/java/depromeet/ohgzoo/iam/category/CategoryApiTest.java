package depromeet.ohgzoo.iam.category;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class CategoryApiTest {

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(new CategoryApi(new CategoryServiceImpl())).build();
    }

    @Test
    public void categoryList_isOk() throws Exception {
        mockMvc.perform(get("/api/v1/category"))
                .andExpect(status().isOk());
    }

    @Test
    public void firstCategoryList_isOk() throws Exception {
        mockMvc.perform(get("/api/v1/firstcategory"))
                .andExpect(status().isOk());
    }

    @Test
    public void secondCategoryList_isOk() throws Exception {
        mockMvc.perform(get("/api/v1/secondcategory"))
                .andExpect(status().isOk());
    }

    @Test
    public void categoryList_returnsCategoryResponse() throws Exception {
        mockMvc.perform(get("/api/v1/category"))
                .andExpect(jsonPath("$.positive").isArray())
                .andExpect(jsonPath("$.positive", hasSize(5)))
                .andExpect(jsonPath("$.positive[0].name", equalTo("기뻐요")))
                .andExpect(jsonPath("$.positive[1].name", equalTo("뿌듯해요")))
                .andExpect(jsonPath("$.positive[2].name", equalTo("안도돼요")))
                .andExpect(jsonPath("$.positive[3].name", equalTo("홀가분해요")))
                .andExpect(jsonPath("$.positive[4].name", equalTo("차분해요")))

                .andExpect(jsonPath("$.negative").isArray())
                .andExpect(jsonPath("$.negative", hasSize(6)))
                .andExpect(jsonPath("$.negative[0].name", equalTo("무기력해요")))
                .andExpect(jsonPath("$.negative[1].name", equalTo("실망했어요")))
                .andExpect(jsonPath("$.negative[2].name", equalTo("슬퍼요")))
                .andExpect(jsonPath("$.negative[3].name", equalTo("후회해요")))
                .andExpect(jsonPath("$.negative[4].name", equalTo("짜증나요")))
                .andExpect(jsonPath("$.negative[5].name", equalTo("불안해요")))

                .andExpect(jsonPath("$.natural").isArray())
                .andExpect(jsonPath("$.natural", hasSize(1)))
                .andExpect(jsonPath("$.natural[0].name", equalTo("모르겠어요")))
        ;
    }

    @Test
    public void firstCategoryList_returnsCategoryResponse() throws Exception {
        mockMvc.perform(get("/api/v1/firstcategory"))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$", hasSize(7)))
                .andExpect(jsonPath("$[0].name", equalTo("무기력해요")))
                .andExpect(jsonPath("$[1].name", equalTo("실망했어요")))
                .andExpect(jsonPath("$[2].name", equalTo("슬퍼요")))
                .andExpect(jsonPath("$[3].name", equalTo("후회해요")))
                .andExpect(jsonPath("$[4].name", equalTo("짜증나요")))
                .andExpect(jsonPath("$[5].name", equalTo("불안해요")))
                .andExpect(jsonPath("$[6].name", equalTo("모르겠어요")));
    }

    @Test
    public void secondCategoryList_returnsCategoryResponse() throws Exception {
        mockMvc.perform(get("/api/v1/secondcategory"))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$", hasSize(12)))
                .andExpect(jsonPath("$[0].name", equalTo("기뻐요")))
                .andExpect(jsonPath("$[1].name", equalTo("뿌듯해요")))
                .andExpect(jsonPath("$[2].name", equalTo("안도돼요")))
                .andExpect(jsonPath("$[3].name", equalTo("홀가분해요")))
                .andExpect(jsonPath("$[4].name", equalTo("차분해요")))
                .andExpect(jsonPath("$[5].name", equalTo("무기력해요")))
                .andExpect(jsonPath("$[6].name", equalTo("실망했어요")))
                .andExpect(jsonPath("$[7].name", equalTo("슬퍼요")))
                .andExpect(jsonPath("$[8].name", equalTo("후회해요")))
                .andExpect(jsonPath("$[9].name", equalTo("짜증나요")))
                .andExpect(jsonPath("$[10].name", equalTo("불안해요")))
                .andExpect(jsonPath("$[11].name", equalTo("모르겠어요")));
    }
}