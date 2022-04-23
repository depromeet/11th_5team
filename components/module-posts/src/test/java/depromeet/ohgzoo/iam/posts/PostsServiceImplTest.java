package depromeet.ohgzoo.iam.posts;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class PostsServiceImplTest {
    private SpyPostsRepository spyPostsRepository;
    private PostsServiceImpl postsService;

    @BeforeEach
    void setUp() {
        spyPostsRepository = new SpyPostsRepository();
        postsService = new PostsServiceImpl(spyPostsRepository);
    }

    @Test
    void createPosts_saveInPostsRepository() {
        CreatePostsRequest request =
                CreatePostsRequest.builder().firstCategory(PostsFirstCategory.NO1)
                        .secondCategory(PostsSecondCategory.NO2).content("blah blah")
                        .tags(Arrays.asList("tag1", "tag2")).disclosure(false).build();

        postsService.createPosts(request);

        assertThat(spyPostsRepository.save_entity.getId()).isNull();
        assertThat(spyPostsRepository.save_entity.getFirstCategory()).isEqualTo(PostsFirstCategory.NO1);
        assertThat(spyPostsRepository.save_entity.getSecondCategory()).isEqualTo(PostsSecondCategory.NO2);
        assertThat(spyPostsRepository.save_entity.getContent()).isEqualTo("blah blah");
        assertThat(spyPostsRepository.save_entity.getTags()).isEqualTo(Arrays.asList("tag1", "tag2"));
        assertThat(spyPostsRepository.save_entity.getDisclosure()).isFalse();
        assertThat(spyPostsRepository.save_entity.getViews()).isZero();
    }

    @Test
    void updatePosts_throwException() {
        assertThatThrownBy(() -> postsService.updatePosts(1L, null, null))
                .isInstanceOf(PostNotFoundException.class);
    }

    @Test
    void createPosts_returnCreatePostsResponse() {
        CreatePostsRequest request =
                CreatePostsRequest.builder().firstCategory(PostsFirstCategory.NO1)
                        .secondCategory(PostsSecondCategory.NO2).content("blah blah")
                        .tags(Arrays.asList("tag1", "tag2")).disclosure(false).build();

        CreatePostsResult result = postsService.createPosts(request);

        assertThat(spyPostsRepository.save_entity.getId()).isEqualTo(result.getPostId());
    }
}