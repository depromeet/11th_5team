package depromeet.ohgzoo.iam.folder;

import depromeet.ohgzoo.iam.folder.folderItem.SpyFolderItemService;
import depromeet.ohgzoo.iam.jwt.LoginMemberArgumentResolver;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;


import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class FolderApiTest {

    private MockMvc mockMvc;
    private SpyFolderService spyFolderService;
    private SpyFolderItemService spyFolderItemService;
    private SpyJwtService spyJwtService;

    FolderRepository folderRepository;

    @BeforeEach
    void setUp() {
        spyFolderService = new SpyFolderService();
        spyFolderItemService = new SpyFolderItemService();
        spyJwtService = new SpyJwtService();
        folderRepository = mock(FolderRepository.class);
        mockMvc = MockMvcBuilders.standaloneSetup(new FolderApi(spyFolderService, spyFolderItemService))
                .setCustomArgumentResolvers(new LoginMemberArgumentResolver(spyJwtService))
                .build();
    }

    @Test
    void addFolder_returnsOkHttpStatus() throws Exception {
        mockMvc.perform(post("/api/v1/folders")
                        .header("AUTH_TOKEN", "givenToken")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"folderName\":\"givenName\"}"))
                .andExpect(status().isOk());
    }

    @Test
    void addFolder_returnsBadRequestWhenNameIsNull() throws Exception {
        mockMvc.perform(post("/api/v1/folders")
                        .header("AUTH_TOKEN", "givenAuth"))
                .andExpect(status().isBadRequest());
    }


    @Test
    void addFolder_passesFolderNameToService() throws Exception {
        mockMvc.perform(post("/api/v1/folders")
                .header("AUTH_TOKEN", "givenAuth")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"folderName\":\"folderName\"}"));

        assertThat(spyFolderService.createFolder_argumentRequest.getFolderName()).isEqualTo("folderName");
    }

    @Test
    void addFolder_returnsFolderResponse() throws Exception {
        spyFolderService.createFolder_returnValue = new FolderResponse(1L, "givenFolderName");

        mockMvc.perform(post("/api/v1/folders")
                        .header("AUTH_TOKEN", "givenAuth")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"folderName\":\"givenFolderName\"}"))
                .andExpect(jsonPath("$.folderId", equalTo(1)))
                .andExpect(jsonPath("$.folderName", equalTo("givenFolderName")));
    }

    @Test
    void deleteFolder_returnsOkHttpStatus() throws Exception {
        mockMvc.perform(delete("/api/v1/folders/1")
                        .header("AUTH_TOKEN", "givenAuth"))
                .andExpect(status().isOk());
    }

    @Test
    void deleteFolder_passesFolderIdToService() throws Exception {
        mockMvc.perform(delete("/api/v1/folders/1")
                .header("AUTH_TOKEN", "givenToken"));

        assertThat(spyFolderService.deleteFolder_argumentId).isEqualTo(1L);
    }

    @Test
    void updateFolder_returnsOKHttpStatus() throws Exception {
        mockMvc.perform(patch("/api/v1/folders/1")
                        .header("AUTH_TOKEN", "givenToken")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"folderName\":\"givenName\"}"))
                .andExpect(status().isOk());
    }

    @Test
    void updateFolder_passesRequestToService() throws Exception {
        spyJwtService.getSubject_returnValue = "1";

        mockMvc.perform(patch("/api/v1/folders/1")
                .header("AUTH_TOKEN", "")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"folderName\":\"givenName\"}"));

        assertThat(spyFolderService.updateFolder_argumentMemberId).isEqualTo(1L);
        assertThat(spyFolderService.updateFolder_argumentFolderId).isEqualTo(1L);
        assertThat(spyFolderService.updateFolder_argumentRequest.getFolderName()).isEqualTo("givenName");
    }

    @Test
    void updateFolder_returnsFolderResponse() throws Exception {
        spyFolderService.updateFolder_returnValue = new FolderResponse(1L, "givenFolderName");

        mockMvc.perform(patch("/api/v1/folders/1")
                        .header("AUTH_TOKEN", "givenToken")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("\"firstCategory\":\"ANGRY\",\"secondCategory\":\"ANXIOUS\",\"content\":\"post content\",\"tags\":[\"orange\",\"apple\"],\"disclosure\":false}"))
                .andExpect(jsonPath("$.folderId", equalTo(1)))
                .andExpect(jsonPath("$.folderName", equalTo("givenFolderName")));
    }

    @Test
    void addFolderItem_OKHttpStatus() throws Exception {
        mockMvc.perform(post("/api/v1/folders/posts/1")
                        .header("AUTH_TOKEN", "givenToken")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"firstCategory\":\"ANGRY\",\"secondCategory\":\"ANXIOUS\",\"content\":\"post content\",\"tags\":[\"orange\",\"apple\"],\"disclosure\":false}"))
                .andExpect(status().isOk());
    }

    @Test
    void addFolderItem_passesFolderNameToService() throws Exception {
        mockMvc.perform(post("/api/v1/folders/posts/1")
                        .header("AUTH_TOKEN", "givenToken")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"firstCategory\":\"ANGRY\",\"secondCategory\":\"ANXIOUS\",\"content\":\"post content\",\"tags\":[\"orange\",\"apple\"],\"disclosure\":false}"))
                .andExpect(status().isOk());

        assertThat(spyFolderItemService.createFolderItem_argumentRequest.getFirstCategory()).isEqualTo(FirstCategory.ANGRY);
    }

    @Test
    void moveFolderItem_OkHttpStatus() throws Exception {
        mockMvc.perform(patch("/api/v1/folders/posts/1")
                        .header("AUTH_TOKEN", "givenToken")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"postId\":1}"))
                .andExpect(status().isOk());
    }

    @Test
    void moveFolderItem_passesFolderNameToService() throws Exception {
        mockMvc.perform(patch("/api/v1/folders/posts/1")
                        .header("AUTH_TOKEN", "givenToken")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"postId\":1}"))
                .andExpect(status().isOk());

        assertThat(spyFolderItemService.moveFolderItem_argumentRequest.getFolderItemId()).isEqualTo(1L);
    }
}
