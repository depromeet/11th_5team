package depromeet.ohgzoo.iam.posts;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@TestPropertySource(properties = {
        "kakaoClientId=[kakao rest id]",
        "kakaoRedirectUrl=[kakao redirect url]"
})
@Transactional
public class PostsRepositoryTest {

    @Autowired
    EntityManager em;

    @Autowired
    PostsRepository postsRepository;

    @Disabled
    @Test
    public void bulkDeletePosts() throws Exception {
        postsRepository.bulkDeletePosts(List.of("1", "2"));
        List<Posts> result = postsRepository.findAll();

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getContent()).isEqualTo("content3");
    }

    @Test
    public void deletePostsByMemberId() {
        postsRepository.save(Posts.builder().id("1").memberId(1L).content("content 1").build());
        postsRepository.save(Posts.builder().id("2").memberId(1L).content("content 2").build());
        postsRepository.deletePostsByMemberId(1L);
        List<Posts> result = postsRepository.findAll();

        assertThat(result).hasSize(0);
    }

}