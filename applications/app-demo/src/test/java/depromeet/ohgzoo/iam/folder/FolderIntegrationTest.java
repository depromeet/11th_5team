package depromeet.ohgzoo.iam.folder;

import depromeet.ohgzoo.iam.IntegrationTest;
import depromeet.ohgzoo.iam.category.FirstCategory;
import depromeet.ohgzoo.iam.category.SecondCategory;
import depromeet.ohgzoo.iam.folder.folderItem.FolderItem;
import depromeet.ohgzoo.iam.folder.folderItem.FolderItemRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Transactional
public class FolderIntegrationTest extends IntegrationTest {

    @Autowired
    FolderRepository folderRepository;
    @Autowired
    FolderItemRepository folderItemRepository;
    @Autowired
    private EntityManager testEntityManager;

    @AfterEach
    void clear() {
        testEntityManager.flush();
        testEntityManager.clear();
        testEntityManager.createNativeQuery("ALTER TABLE folder ALTER COLUMN folder_id RESTART WITH 1").executeUpdate();
        testEntityManager.createNativeQuery("ALTER TABLE folder_item ALTER COLUMN folder_item_id RESTART WITH 1").executeUpdate();
    }

    @BeforeEach
    void setUp() {
        Folder folder1 = folderGenerator();
        folderRepository.save(folder1);

        Folder folder2 = folderGenerator();
        folderRepository.save(folder2);

        FolderItem folderItem = folderItemGenerator();
        folderItemRepository.save(folderItem);
        folderItem.setFolder(folder1);
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
    void getFolders() throws Exception {
        mockMvc.perform(get("/api/v1/folders"))
                .andExpect(status().isOk());
    }

    @Test
    void addFolderItem() throws Exception {
        mockMvc.perform(post("/api/v1/folders/posts/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"firstCategory\":\"ANGRY\",\"secondCategory\":\"ANXIOUS\",\"content\":\"post content\",\"tags\":[\"orange\",\"apple\"],\"disclosure\":false,\"postId\":10}"))
                .andExpect(status().isOk());
    }

    @Test
    void moveFolderItem() throws Exception {
        System.out.println(folderItemRepository.findAll().get(0).getPostId());
        mockMvc.perform(patch("/api/v1/folders/posts/2")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"postId\":1}"))
                .andExpect(status().isOk());
    }

    private Folder folderGenerator() {
        return Folder.builder()
                .name("folder name")
                .memberId(1L)
                .coverImg("cover image").build();
    }

    private FolderItem folderItemGenerator() {
        return FolderItem.builder()
                .firstCategory(FirstCategory.ANGRY)
                .secondCategory(SecondCategory.UPSET)
                .content("post content")
                .disclosure(false)
                .postId(1L)
                .memberId(1L)
                .build();
    }
}
