package depromeet.ohgzoo.iam.posts;

import depromeet.ohgzoo.iam.category.FirstCategory;
import depromeet.ohgzoo.iam.category.SecondCategory;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

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
        Long memberId = 1L;
        CreatePostsRequest request =
                CreatePostsRequest.builder().firstCategory(FirstCategory.NO1)
                        .secondCategory(SecondCategory.Idk).content("blah blah")
                        .tags(Arrays.asList("tag1", "tag2")).disclosure(false).build();

        postsService.createPosts(memberId, request);

        assertThat(spyPostsRepository.save_entity.getId()).isNull();
        assertThat(spyPostsRepository.save_entity.getMemberId()).isEqualTo(1L);
        assertThat(spyPostsRepository.save_entity.getFirstCategory()).isEqualTo(FirstCategory.NO1);
        assertThat(spyPostsRepository.save_entity.getSecondCategory()).isEqualTo(SecondCategory.Idk);
        assertThat(spyPostsRepository.save_entity.getContent()).isEqualTo("blah blah");
        assertThat(spyPostsRepository.save_entity.getTags()).isEqualTo(Arrays.asList("tag1", "tag2"));
        assertThat(spyPostsRepository.save_entity.isDisclosure()).isFalse();
        assertThat(spyPostsRepository.save_entity.getViews()).isZero();
    }

    @Test
    void updatePosts_PostsNotFoundException() {
        assertThatThrownBy(() -> postsService.updatePosts(0L, null, null))
                .isInstanceOf(PostsNotFoundException.class);
    }

    @Test
    void updatePosts_AccessDeniedException() {
        assertThatThrownBy(() -> postsService.updatePosts(1L, null, 2L))
                .isInstanceOf(AccessDeniedException.class);
    }

    @Test
    void deletePosts_AccessDeniedException() {
        List<Long> postIds = List.of(1L, 2L);
        assertThatThrownBy(() -> postsService.deletePosts(postIds, 2L))
                .isInstanceOf(AccessDeniedException.class);
    }

    @Test
    void createPosts_returnCreatePostsResponse() {
        Long memberId = 1L;
        CreatePostsRequest request =
                CreatePostsRequest.builder().firstCategory(FirstCategory.NO1)
                        .secondCategory(SecondCategory.Idk).content("blah blah")
                        .tags(Arrays.asList("tag1", "tag2")).disclosure(false).build();

        CreatePostsResult result = postsService.createPosts(memberId, request);

        assertThat(spyPostsRepository.save_entity.getId()).isEqualTo(result.getPostId());
    }

    @Test
    void getPostsByMemberId_passesMemberIdToRepository() {
        postsService.getPostsByMemberId(1L, 0, 0);

        assertThat(spyPostsRepository.findByMemberId_argumentId).isEqualTo(1L);
    }

    @Test
    void getPostsByMemberId_returnsPagingPosts() {
        spyPostsRepository.findByMemberId_returnValue = List.of(
                Posts.builder().id(1L).build(),
                Posts.builder().id(2L).build(),
                Posts.builder().id(3L).build(),
                Posts.builder().id(4L).build()
        );

        List<PostsDto> result = postsService.getPostsByMemberId(1L, 2, 2);

        assertThat(result).containsExactly(
                PostsDto.builder().id(3L).build(),
                PostsDto.builder().id(4L).build()
        );
    }

    @Test
    void getPostsByTag_callsFindAllInRepository() {
        postsService.getPostsByTag("", 0, 0);

        assertThat(spyPostsRepository.findAll_wasCalled).isTrue();
    }

    @Test
    void getPostsByTag_returnsFilteredPosts() {
        spyPostsRepository.findAll_returnValue = List.of(
                Posts.builder().id(1L).tags(List.of("1")).build(),
                Posts.builder().id(2L).tags(List.of("1", "2")).build(),
                Posts.builder().id(3L).tags(List.of("2", "3")).build(),
                Posts.builder().id(4L).tags(List.of("1", "2", "3")).build()
        );

        List<PostsDto> result = postsService.getPostsByTag("3", 0, 2);

        assertThat(result).containsExactly(
                PostsDto.builder().id(3L).tags(List.of("2", "3")).build(),
                PostsDto.builder().id(4L).tags(List.of("1", "2", "3")).build()
        );
    }

    @Test
    void getPostsByTag_returnsPagingPosts() {
        spyPostsRepository.findAll_returnValue = List.of(
                Posts.builder().id(1L).tags(List.of("1")).build(),
                Posts.builder().id(2L).tags(List.of("1", "2")).build(),
                Posts.builder().id(3L).tags(List.of("2", "3")).build(),
                Posts.builder().id(4L).tags(List.of("1", "2", "3")).build()
        );

        List<PostsDto> result = postsService.getPostsByTag("3", 1, 1);

        assertThat(result).containsExactly(
                PostsDto.builder().id(4L).tags(List.of("1", "2", "3")).build()
        );
    }

    @Test
    void getPostsOrderByPopular_callsFindAllInRepository() {
        postsService.getPostsOrderByPopular(0, 0);

        assertThat(spyPostsRepository.findAll_wasCalled).isTrue();
    }

    @Test
    void getPostsOrderByPopular_returnsOrderedPosts() {
        spyPostsRepository.findAll_returnValue = List.of(
                Posts.builder().id(1L).views(1).build(),
                Posts.builder().id(2L).views(10).build(),
                Posts.builder().id(3L).views(2).build(),
                Posts.builder().id(4L).views(5).build()
        );

        List<PostsDto> result = postsService.getPostsOrderByPopular(0, 4);

        assertThat(result).containsExactly(
                PostsDto.builder().id(2L).views(10).build(),
                PostsDto.builder().id(4L).views(5).build(),
                PostsDto.builder().id(3L).views(2).build(),
                PostsDto.builder().id(1L).views(1).build()
        );
    }

    @Test
    void getPostsOrderByPopular_returnsPagingPosts() {
        spyPostsRepository.findAll_returnValue = List.of(
                Posts.builder().id(1L).views(1).build(),
                Posts.builder().id(2L).views(10).build(),
                Posts.builder().id(3L).views(2).build(),
                Posts.builder().id(4L).views(5).build()
        );

        List<PostsDto> result = postsService.getPostsOrderByPopular(2, 2);

        assertThat(result).containsExactly(
                PostsDto.builder().id(3L).views(2).build(),
                PostsDto.builder().id(1L).views(1).build()
        );
    }

    @Test
    void getRecentlyUnwrittenPosts_passesMemberIdToRepository() {
        spyPostsRepository.findByMemberId_returnValue = List.of(
                Posts.builder().id(3L).secondCategory(SecondCategory.Unwritten).createdAt(LocalDateTime.now()).build()
        );

        postsService.getRecentlyUnwrittenPosts(1L);

        assertThat(spyPostsRepository.findByMemberId_argumentId).isEqualTo(1L);
    }

    @Test
    void getRecentlyUnwrittenPosts_throwsPostsNotFoundException_whenRecentlyUnwrittenPostsIsEmpty() {
        Assertions.assertThatThrownBy(() -> postsService.getRecentlyUnwrittenPosts(1L))
                .isInstanceOf(PostsNotFoundException.class);
    }

    @Test
    void getRecentlyUnwrittenPosts_returnsPagingPosts() {
        LocalDateTime now = LocalDateTime.now().withHour(0).withMinute(0).withSecond(0).withNano(0);

        LocalDateTime before7Day = now.minusDays(6).minusNanos(1);
        LocalDateTime before8Day = now.minusDays(7).minusNanos(1);

        spyPostsRepository.findByMemberId_returnValue = List.of(
                Posts.builder().id(1L).secondCategory(SecondCategory.NO1).build(),
                Posts.builder().id(2L).secondCategory(SecondCategory.Idk).build(),
                Posts.builder().id(3L).secondCategory(SecondCategory.Unwritten).createdAt(before7Day).build(),
                Posts.builder().id(4L).secondCategory(SecondCategory.Unwritten).createdAt(before8Day).build()
        );

        PostsDto result = postsService.getRecentlyUnwrittenPosts(1L);

        assertThat(result).usingRecursiveComparison()
                .isEqualTo(PostsDto.builder().id(3L).secondCategory(SecondCategory.Unwritten).createdAt(before7Day).build());
    }

    @Test
    public void increaseViews() throws Exception {
        postsService.increaseViews(1L);
        assertThat(spyPostsRepository.findById.get().getViews()).isEqualTo(1);
    }
}