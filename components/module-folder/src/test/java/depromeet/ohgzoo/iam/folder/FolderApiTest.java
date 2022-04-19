package depromeet.ohgzoo.iam.folder;


import depromeet.ohgzoo.iam.jwt.JwtServiceImpl;
import depromeet.ohgzoo.iam.member.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class FolderApiTest {
//- 폴더는 (이름, 게시글 목록, 폴더주인, 폴더 커버 이미지) 을 가지고 있다.
//- 유저는 폴더를 추가할 수 있다.
//- 유저는 폴더를 삭제할 수 있다.
//- 유저는 폴더의 이름을 수정할 수 있다.

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
                .param("name", "folderName"));

        assertThat(spyFolderService.createFolder_argumentName).isEqualTo("folderName");
    }

    @Test
    void addFolder_returnsFolderResponse() throws Exception {
        spyFolderService.createFolder_returnValue = new FolderResponse(1L, "givenFolderName");

        mockMvc.perform(post("/api/v1/folders")
                        .header("AUTH_TOKEN", "givenAuth")
                        .param("name", "givenFolderName"))
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
        assertThat(spyFolderService.updateFolder_argumentRequest.getName()).isEqualTo("givenName");
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

//    @Test
//    void updateFolder_throwsException_whenRequestNameIsDuplicated(){
//        //들어오는 값에서도 중복이 있으면 안됨
//        new UpdateFolderRequest("")
//    }

}
