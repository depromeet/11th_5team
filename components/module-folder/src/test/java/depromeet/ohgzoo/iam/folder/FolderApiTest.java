package depromeet.ohgzoo.iam.folder;

import depromeet.ohgzoo.iam.category.FirstCategory;
import depromeet.ohgzoo.iam.category.ImageLoader;
import depromeet.ohgzoo.iam.folder.folderItem.FolderItem;
import depromeet.ohgzoo.iam.folder.folderItem.FolderItemDto;
import depromeet.ohgzoo.iam.folder.folderItem.FolderItemsGetResponse;
import depromeet.ohgzoo.iam.folder.folderItem.SpyFolderItemService;
import depromeet.ohgzoo.iam.jwt.LoginMemberArgumentResolver;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static depromeet.ohgzoo.iam.folder.folderItem.FolderItemFixtures.aFolderItem;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
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
        mockMvc = MockMvcBuilders.standaloneSetup(new FolderApi(spyFolderService))
                .setCustomArgumentResolvers(new LoginMemberArgumentResolver(spyJwtService))
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
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
                .header("AUTH_TOKEN", "givenToken")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"folderName\":\"givenName\"}"));

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
        mockMvc.perform(post("/api/v1/folders/1/posts")
                        .header("AUTH_TOKEN", "givenToken")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"firstCategory\":\"SADNESS\",\"secondCategory\":\"ANXIOUS\",\"content\":\"post content\",\"tags\":[\"orange\",\"apple\"],\"disclosure\":false,\"postId\":1}"))
                .andExpect(status().isOk());
    }

    @Test
    void addFolderItem_passesFolderNameToService() throws Exception {
        mockMvc.perform(post("/api/v1/folders/1/posts")
                        .header("AUTH_TOKEN", "givenToken")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"firstCategory\":\"SADNESS\",\"secondCategory\":\"ANXIOUS\",\"content\":\"post content\",\"tags\":[\"orange\",\"apple\"],\"disclosure\":false,\"postId\":1}"))
                .andExpect(status().isOk());

        assertThat(spyFolderService.createFolderItem_argumentRequest.getFirstCategory()).isEqualTo(FirstCategory.SADNESS);
    }

    @Test
    void moveFolderItem_OkHttpStatus() throws Exception {
        mockMvc.perform(patch("/api/v1/folders/1/posts")
                        .header("AUTH_TOKEN", "givenToken")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"postId\":1}"))
                .andExpect(status().isOk());
    }

    @Test
    void moveFolderItem_passesFolderNameToService() throws Exception {
        mockMvc.perform(patch("/api/v1/folders/1/posts")
                        .header("AUTH_TOKEN", "givenToken")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"postId\":1}"))
                .andExpect(status().isOk());

        assertThat(spyFolderService.moveFolderItem_argumentRequest.getFolderItemId()).isEqualTo("1");
    }

    @Test
    void deleteAllFolderItems_returnsOkHttpStatus() throws Exception {
        mockMvc.perform(delete("/api/v1/folders/1/posts")
                        .header("AUTH_TOKEN", "givenToken"))
                .andExpect(status().isOk());
    }

    @Test
    void deleteAllFolderItems_passesFolderIdToService() throws Exception {
        mockMvc.perform(delete("/api/v1/folders/1/posts")
                .header("AUTH_TOKEN", "givenToken"));

        assertThat(spyFolderService.deleteAllFolderItems_argumentFolderId).isEqualTo(1L);
    }


    @Test
    void getFolders_OKHttpStatus() throws Exception {
        mockMvc.perform(get("/api/v1/folders")
                        .header("AUTH_TOKEN", "givenToken"))
                .andExpect(status().isOk());
    }

    @Test
    void getFolders_returnsListResponse() throws Exception {
        spyJwtService.getSubject_returnValue = "1";
        Folder folder = Folder.builder().memberId(1L).id(1L).name("folder name").build();
        List<FolderGetResponse> folders = new ArrayList<FolderGetResponse>(Arrays.asList(FolderGetResponse.of(folder)));
        spyFolderService.getFolders_returnValue = new FoldersGetResponse(folders, new ArrayList<String>(Arrays.asList(ImageLoader.DEFAULT_IMAGE, ImageLoader.DEFAULT_IMAGE, ImageLoader.DEFAULT_IMAGE, ImageLoader.DEFAULT_IMAGE)));

        mockMvc.perform(get("/api/v1/folders")
                        .header("AUTH_TOKEN", "givenToken"))
                .andExpect(jsonPath("$.['folders'][0].folderId", equalTo(1)))
                .andExpect(jsonPath("$['folders'][0].folderName", equalTo("folder name")))
                .andExpect(jsonPath("$['postsThumbnail'][0]", equalTo(ImageLoader.DEFAULT_IMAGE)));
    }

    @Test
    void getFolderItems_OKHttpStatus() throws Exception {
        mockMvc.perform(get("/api/v1/folders/1/posts?page=1&size=20")
                        .header("AUTH_TOKEN", "givenToken"))
                .andExpect(status().isOk());
    }

    @Test
    void getFolderItems_returnsResponse() throws Exception {
        Folder folder = Folder.builder().id(1L).build();
        FolderItem folderItem1 = aFolderItem().postId("1").build();
        FolderItem folderItem2 = aFolderItem().postId("2").build();
        folderItem1.setFolder(folder);
        folderItem2.setFolder(folder);
        spyFolderService.getFolderItems_returnValue = new FolderItemsGetResponse(2,
                "미분류",
                true, List.of(FolderItemDto.of(folderItem1), FolderItemDto.of(folderItem2)));

        mockMvc.perform(get("/api/v1/folders/1/posts?page=1&size=20")
                        .header("AUTH_TOKEN", "givenToken"))
                .andDo(print())
                .andExpect(jsonPath("$.totalCount", equalTo(2)))
                .andExpect(jsonPath("$.posts[0].postId", equalTo("1")))
                .andExpect(jsonPath("$.posts[1].postId", equalTo("2")));
    }

    @Test
    void getFolderByPost_returnsOkHttpStatus() throws Exception {
        mockMvc.perform(get("/api/v1/folders/posts/1"))
                .andExpect(status().isOk());
    }

    @Test
    void getFolderByPost_returnsFolderGetResponse() throws Exception {
        spyFolderService.getFolderByPost_returnValue = new FolderGetResponse(1L, false, "folder name", "cover image", 1);

        mockMvc.perform(get("/api/v1/folders/posts/1"))
                .andExpect(jsonPath("$.folderId", equalTo(1)))
                .andExpect(jsonPath("$.default", equalTo(false)))
                .andExpect(jsonPath("$.folderName", equalTo("folder name")))
                .andExpect(jsonPath("$.coverImg", equalTo("cover image")))
                .andExpect(jsonPath("$.postCount", equalTo(1)))
        ;
    }

    @Test
    void getFolderByPost_passesPostIdToService() throws Exception {
        mockMvc.perform(get("/api/v1/folders/posts/1"));

        assertThat(spyFolderService.getFolderByPost_argumentPostId).isEqualTo("1");
    }
}