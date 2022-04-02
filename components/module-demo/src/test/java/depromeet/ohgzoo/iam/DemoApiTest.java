package depromeet.ohgzoo.iam;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class DemoApiTest {

    private MockMvc mockMvc;
    private SpyDemoService spyDemoService;

    @BeforeEach
    void setUp() {
        spyDemoService = new SpyDemoService();
        mockMvc = MockMvcBuilders.standaloneSetup(new DemoApi(spyDemoService)).build();
    }

    @Test
    void demo_returnsOkHttpStatus() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().isOk());
    }

    @Test
    void demo_returnsDemo() throws Exception {
        spyDemoService.getDemo_returnValue = new Demo("hello world");

        mockMvc.perform(get("/"))
                .andExpect(jsonPath("$.msg", equalTo("hello world")));
    }
}