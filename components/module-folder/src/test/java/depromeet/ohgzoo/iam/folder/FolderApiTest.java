package depromeet.ohgzoo.iam.folder;

import depromeet.ohgzoo.iam.jwt.JwtServiceImpl;
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

    FolderRepository folderRepository;

    @BeforeEach
    void setUp() {
        spyFolderService = new SpyFolderService();
        folderRepository = mock(FolderRepository.class);
        FolderService folderService = new FolderServiceImpl(new JwtServiceImpl(), folderRepository);

        mockMvc = MockMvcBuilders.standaloneSetup(new FolderApi(spyFolderService))
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
    void addFolder_returnsBadRequest() throws Exception {
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
                        .content("{}"))
                .andExpect(status().isOk());
    }

    @Test
    void updateFolder_passesRequestToService() throws Exception {
        mockMvc.perform(patch("/api/v1/folders/1")
                .header("AUTH_TOKEN", "givenToken")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"givenName\"}"));

        assertThat(spyFolderService.updateFolder_argumentAuthToken).isEqualTo("givenToken");
        assertThat(spyFolderService.updateFolder_argumentFolderId).isEqualTo(1L);
        assertThat(spyFolderService.updateFolder_argumentRequest.getFolderName()).isEqualTo("givenName");
    }

    @Test
    void updateFolder_returnsFolderResponse() throws Exception {
        spyFolderService.updateFolder_returnValue = new FolderResponse(1L, "givenFolderName");

        mockMvc.perform(patch("/api/v1/folders/1")
                        .header("AUTH_TOKEN", "givenToken")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(jsonPath("$.folderId", equalTo(1)))
                .andExpect(jsonPath("$.folderName", equalTo("givenFolderName")));
    }
}
