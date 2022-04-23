package depromeet.ohgzoo.iam.posts;

import org.junit.jupiter.api.BeforeEach;
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

    @BeforeEach
    public void init() {
        for (int i = 1; i <= 10; i++) {
            Posts posts = Posts.builder().content(String.valueOf(i)).build();
            em.persist(posts);
        }
        em.flush();
        em.clear();
    }

    @Test
    public void bulkDeletePosts() throws Exception {
        List<Posts> posts = postsRepository.findAll();
        em.clear();
        postsRepository.bulkDeletePosts(List.of(posts.get(0).getId(), posts.get(1).getId(), posts.get(2).getId()));
        List<Posts> result = postsRepository.findAll();

        assertThat(result.size()).isEqualTo(7);
        assertThat(result.get(0).getContent()).isEqualTo("4");
        assertThat(result.get(result.size() - 1).getContent()).isEqualTo("10");
    }

}