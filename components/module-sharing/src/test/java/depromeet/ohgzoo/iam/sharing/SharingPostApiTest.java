package depromeet.ohgzoo.iam.sharing;

import com.fasterxml.jackson.databind.ObjectMapper;
import depromeet.ohgzoo.iam.category.SecondCategory;
import depromeet.ohgzoo.iam.jwt.LoginMemberArgumentResolver;
import depromeet.ohgzoo.iam.posts.UpdatePostsRequest;
import depromeet.ohgzoo.iam.sharing.jwt.SpyJwtService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class SharingPostApiTest {
    private final ObjectMapper objectMapper = new ObjectMapper();
    private MockMvc mockMvc;
    private SpySharingPostService spySharingPostService;
    private SpyJwtService spyJwtService;

    @BeforeEach
    void setUp() {
        spyJwtService = new SpyJwtService();
        spySharingPostService = new SpySharingPostService();
        mockMvc = MockMvcBuilders.standaloneSetup(new SharingPostApi(spySharingPostService))
                .setCustomArgumentResolvers(new LoginMemberArgumentResolver(spyJwtService)).build();
    }

    @Test
    void sharingPost_returnsOkHttpStatus() throws Exception {
        SharingRequest request = SharingRequest.builder()
                .receiverName("receiver").category(SharingCategory.UNSELECT).postId("1").build();
        String json = objectMapper.writeValueAsString(request);

        mockMvc.perform(post("/api/v1/sharing")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isCreated());
    }

    @Test
    void sharingPost_returnsSharingResult() throws Exception {
        spyJwtService.getSubject_returnValue = "1";
        SharingRequest request = SharingRequest.builder()
                .receiverName("receiver").category(SharingCategory.UNSELECT).postId("1").build();
        String json = objectMapper.writeValueAsString(request);

        spySharingPostService.sharingPost_returnValue = new SharingResponse("testLink");

        mockMvc.perform(post("/api/v1/sharing")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andDo(print())
                .andExpect(jsonPath("$.link", equalTo("testLink")));
    }

    @Test
    void sharingPost_passesArgumentsToService() throws Exception {
        spyJwtService.getSubject_returnValue = "1";
        SharingRequest request = SharingRequest.builder()
                .receiverName("receiver").category(SharingCategory.UNSELECT).postId("1").build();
        String json = objectMapper.writeValueAsString(request);
        System.out.println("json = " + json);

        mockMvc.perform(post("/api/v1/sharing")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)).andDo(print());

        assertThat(spySharingPostService.sharingPost_argumentMemberId).isEqualTo(1L);
        assertThat(spySharingPostService.sharingPost_argumentRequest.getPostId()).isEqualTo("1");
        assertThat(spySharingPostService.sharingPost_argumentRequest.getReceiverName()).isEqualTo("receiver");
        assertThat(spySharingPostService.sharingPost_argumentRequest.getCategory()).isEqualTo(SharingCategory.UNSELECT);
        assertThat(spySharingPostService.sharingPost_argumentRequest.getPostId()).isEqualTo("1");
    }

    @Test
    void getSharingPost_returnsOkHttpStatus() throws Exception {
        mockMvc.perform(get("/api/v1/sharing/MSwyLDM="))
                .andExpect(status().isOk());
    }

    @Test
    void getSharingPost_passesLinkToService() throws Exception {
        mockMvc.perform(get("/api/v1/sharing/MSwyLDM="))
                .andExpect(status().isOk());

        assertThat(spySharingPostService.getSharingPost_argumentLink).isEqualTo("MSwyLDM=");
        assertThat(spySharingPostService.getSharingPost_sharingPostId).isEqualTo("1");
        assertThat(spySharingPostService.getSharingPost_postId).isEqualTo("2");
        assertThat(spySharingPostService.getSharingPost_memberId).isEqualTo("3");
    }
}
