package depromeet.ohgzoo.iam.search;

import depromeet.ohgzoo.iam.IntegrationTest;
import depromeet.ohgzoo.iam.search.batch.SearchEntity;
import depromeet.ohgzoo.iam.search.batch.SearchRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Transactional
public class SearchIntegrationTest extends IntegrationTest {

    @Autowired
    private SearchRepository searchRepository;

    @BeforeEach
    void setUp() {
        searchRepository.save(new SearchEntity("id1", 1L, "First Category", "Second Category",
                "content1",
                List.of("tag1", "tag2"),
                1,
                LocalDateTime.now()));
    }

    @Test
    void search() throws Exception {
        mockMvc.perform(get("/api/v1/search")
                        .param("keyword", "content"))
                .andExpect(status().isOk());
    }
}
