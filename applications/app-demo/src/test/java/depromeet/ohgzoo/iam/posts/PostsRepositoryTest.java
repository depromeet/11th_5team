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
        postsRepository.bulkDeletePosts(List.of(1L, 2L, 3L));
        List<Posts> posts = postsRepository.findAll();

        assertThat(posts.size()).isEqualTo(7);
        assertThat(posts.get(0).getContent()).isEqualTo("4");
        assertThat(posts.get(posts.size() - 1).getContent()).isEqualTo("10");
    }

}