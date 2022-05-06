package depromeet.ohgzoo.iam.folder;

import depromeet.ohgzoo.iam.IntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import javax.transaction.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Transactional
public class FolderIntegrationTest extends IntegrationTest {

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
    void getFolderItems() throws Exception {
        mockMvc.perform(get("/api/v1/folders/posts/1?page=0&size=20")
                        .header("AUTH_TOKEN", "givenToken"))
                .andExpect(status().isOk());
    }

    @Test
    void moveFolderItem() throws Exception {
        mockMvc.perform(patch("/api/v1/folders/posts/2")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"postId\":1}"))
                .andExpect(status().isOk());
    }

    @Test
    void deleteFolderItem() throws Exception {
        mockMvc.perform(delete("/api/v1/folders/posts/1")
                .header("AUTH_TOKEN", "givenToken")
        ).andExpect(status().isOk());
    }
}
