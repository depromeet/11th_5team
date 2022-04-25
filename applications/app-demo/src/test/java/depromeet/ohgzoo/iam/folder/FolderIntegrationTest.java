package depromeet.ohgzoo.iam.folder;

import depromeet.ohgzoo.iam.IntegrationTest;
import depromeet.ohgzoo.iam.folder.folderItem.FolderItem;
import depromeet.ohgzoo.iam.folder.folderItem.FolderItemRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class FolderIntegrationTest extends IntegrationTest {

    @Autowired
    FolderRepository folderRepository;
    @Autowired
    FolderItemRepository folderItemRepository;

    @BeforeEach
    void setUp() {
        folderRepository.deleteAll();
        folderItemRepository.deleteAll();
        Folder folder1 = Folder.builder()
                .id(1L)
                .name("folder name")
                .memberId(1L)
                .coverImg("cover image").build();
        folderRepository.save(folder1);

        Folder folder2 = Folder.builder()
                .id(2L)
                .name("folder name")
                .memberId(1L)
                .coverImg("cover image").build();
        folderRepository.save(folder2);

        FolderItem folderItem = FolderItem.builder()
                .id(1L)
                .firstCategory(FirstCategory.ANGRY)
                .secondCategory(SecondCategory.UPSET)
                .content("post content")
                .disclosure(false)
                .folder(folder1)
                .build();

        folderItemRepository.save(folderItem);
        folder1.addFolderItem(folderItem);
    }

    @Test
    void addFolder() throws Exception {
        mockMvc.perform(post("/api/v1/folders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"folderName\":\"givenName\"}"))
                .andExpect(status().isOk());
    }

    @Test
    void deleteFolder() throws Exception {
        mockMvc.perform(delete("/api/v1/folders/1"))
                .andExpect(status().isOk());
    }

    @Test
    void updateFolder() throws Exception {
        mockMvc.perform(patch("/api/v1/folders/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"folderName\":\"givenName\"}"))
                .andExpect(status().isOk());
    }

    @Test
    void addFolderItem() throws Exception {
        mockMvc.perform(post("/api/v1/folders/posts/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"firstCategory\":\"ANGRY\",\"secondCategory\":\"ANXIOUS\",\"content\":\"post content\",\"tags\":[\"orange\",\"apple\"],\"disclosure\":false}"))
                .andExpect(status().isOk());
    }

    @Test
    void moveFolderItem() throws Exception {
        mockMvc.perform(patch("/api/v1/folders/posts/2")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"postId\":1}"))
                .andExpect(status().isOk());
    }
}
