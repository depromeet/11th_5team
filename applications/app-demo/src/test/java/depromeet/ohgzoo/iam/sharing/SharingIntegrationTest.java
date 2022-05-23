package depromeet.ohgzoo.iam.sharing;

import com.fasterxml.jackson.databind.ObjectMapper;
import depromeet.ohgzoo.iam.IntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBatchTest
public class SharingIntegrationTest extends IntegrationTest {
    @Autowired
    private SharingPostRepository sharingPostRepository;
    ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void sharingPost() throws Exception {
        SharingRequest request = SharingRequest.builder()
                .receiverName("receiver").category(SharingCategory.UNSELECT).postId("1").build();
        String json = objectMapper.writeValueAsString(request);

        mockMvc.perform(post("/api/v1/sharing")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isCreated());
    }

    @Test
    void getSharingPost() throws Exception {
        mockMvc.perform(get("/api/v1/sharing/MSwyLDM="))
                .andExpect(status().isOk());
    }
}
