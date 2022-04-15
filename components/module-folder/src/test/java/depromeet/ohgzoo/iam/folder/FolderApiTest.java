package depromeet.ohgzoo.iam.folder;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
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

    @BeforeEach
    void setUp() {
        spyFolderService = new SpyFolderService();
        mockMvc = MockMvcBuilders.standaloneSetup(new FolderApi(spyFolderService)).build();
    }

    @Test
    void addFolder_returnsOkHttpStatus() throws Exception {
        mockMvc.perform(post("/api/v1/folder")
                        .header("AUTH_TOKEN", "givenAuth")
                        .param("name", "folderName"))
                .andExpect(status().isOk());
    }

    @Test
    void addFolder_throwsExceptionWhenNameIsNull() throws Exception {
        mockMvc.perform(post("/api/v1/folder")
                .header("AUTH_TOKEN", "givenToken")
        ).andExpect(status().isBadRequest());
    }

    @Test
    void addFolder_returnsBadRequest() throws Exception {
        mockMvc.perform(post("/api/v1/folder").header("AUTH_TOKEN", "givenAuth"))
                .andExpect(status().isBadRequest());
    }


    @Test
    void addFolder_passesFolderNameToService() throws Exception {
        mockMvc.perform(post("/api/v1/folder")
                .header("AUTH_TOKEN", "givenAuth")
                .param("name", "folderName"));

        assertThat(spyFolderService.createFolder_argumentName).isEqualTo("folderName");
    }

    @Test
    void addFolder_returnsFolderResponse() throws Exception {
        spyFolderService.createFolder_returnValue = new FolderResponse(1L, "givenFolderName");

        mockMvc.perform(post("/api/v1/folder")
                        .header("AUTH_TOKEN", "givenAuth")
                        .param("name", "givenFolderName"))
                .andExpect(jsonPath("$.folderId", equalTo(1)))
                .andExpect(jsonPath("$.folderName", equalTo("givenFolderName")));
    }

    @Test
    void deleteFolder_returnsOkHttpStatus() throws Exception {
        mockMvc.perform(delete("/api/v1/folder")
                        .header("AUTH_TOKEN", "givenAuth")
                        .param("id", "1"))
                .andExpect(status().isOk());
    }

    @Test
    void deleteFolder_throwsExceptionWhenNameIsNull() throws Exception {
        mockMvc.perform(post("/api/v1/folder")
                .header("AUTH_TOKEN", "givenToken")
        ).andExpect(status().isBadRequest());
    }

    @Test
    void deleteFolder_passesFolderIdToService() throws Exception {
        mockMvc.perform(delete("/api/v1/folder")
                .header("AUTH_TOKEN","givenToken")
                .param("id","1"));

        assertThat(spyFolderService.deleteFolder_argumentId).isEqualTo("1");
    }

}