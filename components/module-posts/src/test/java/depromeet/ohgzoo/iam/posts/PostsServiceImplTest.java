package depromeet.ohgzoo.iam.posts;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

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
        Long memberId = 1L;
        CreatePostsRequest request =
                CreatePostsRequest.builder().firstCategory(PostsFirstCategory.NO1)
                        .secondCategory(PostsSecondCategory.NO2).content("blah blah")
                        .tags(Arrays.asList("tag1", "tag2")).disclosure(false).build();

        postsService.createPosts(memberId, request);

        assertThat(spyPostsRepository.save_entity.getId()).isNull();
        assertThat(spyPostsRepository.save_entity.getMemberId()).isEqualTo(1L);
        assertThat(spyPostsRepository.save_entity.getFirstCategory()).isEqualTo(PostsFirstCategory.NO1);
        assertThat(spyPostsRepository.save_entity.getSecondCategory()).isEqualTo(PostsSecondCategory.NO2);
        assertThat(spyPostsRepository.save_entity.getContent()).isEqualTo("blah blah");
        assertThat(spyPostsRepository.save_entity.getTags()).isEqualTo(Arrays.asList("tag1", "tag2"));
        assertThat(spyPostsRepository.save_entity.getDisclosure()).isFalse();
        assertThat(spyPostsRepository.save_entity.getViews()).isZero();
    }

    @Test
    void createPosts_returnCreatePostsResponse() {
        Long memberId = 1L;
        CreatePostsRequest request =
                CreatePostsRequest.builder().firstCategory(PostsFirstCategory.NO1)
                        .secondCategory(PostsSecondCategory.NO2).content("blah blah")
                        .tags(Arrays.asList("tag1", "tag2")).disclosure(false).build();

        CreatePostsResult result = postsService.createPosts(memberId, request);

        assertThat(spyPostsRepository.save_entity.getId()).isEqualTo(result.getPostId());
    }

//    @Test
//    void findAllPostsOfMe_passesMemberIdToRepository() {
//        Long memberId = 1L;
//        postsService.findAllPostsOfMe(memberId, PageRequest.of(0, 20));
//
//        assertThat(spyPostsRepository.findByMemberId_argumentId).isEqualTo(memberId);
//    }
//
//    @Test
//    void findPostsByTag_passesTagToRepository() {
//        String tag = "tag";
//        postsService.findPostsByTag(tag, PageRequest.of(0, 20));
//
//        assertThat(spyPostsRepository.findByTag_argumentTag).isEqualTo(tag);
//    }
//
//    @Test
//    void findRecentPostWhereSecondCategoryIsNull_passesMemberIdToRepository() {
//        Long memberId = 1L;
//        postsService.findRecentPostWhereSecondCategoryIsNull(memberId);
//
//        assertThat(spyPostsRepository.findByMemberId_argumentId).isEqualTo(memberId);
//    }
}
