package depromeet.ohgzoo.iam.posts;

import depromeet.ohgzoo.iam.category.CategoryService;
import depromeet.ohgzoo.iam.category.CategoryServiceImpl;
import depromeet.ohgzoo.iam.category.FirstCategory;
import depromeet.ohgzoo.iam.category.SecondCategory;
import depromeet.ohgzoo.iam.posts.CategoryItemsResponse.CategoryItemDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class PostsServiceImplTest {
    private SpyPostsRepository spyPostsRepository;
    private PostsServiceImpl postsService;
    private CategoryService categoryService;

    @BeforeEach
    void setUp() {
        categoryService = new CategoryServiceImpl();
        spyPostsRepository = new SpyPostsRepository();
        postsService = new PostsServiceImpl(spyPostsRepository, categoryService);
    }

    @Test
    void createPosts_saveInPostsRepository() {
        Long memberId = 1L;
        CreatePostsRequest request =
                CreatePostsRequest.builder()
                        .postId("postId")
                        .firstCategory(FirstCategory.SADNESS)
                        .secondCategory(SecondCategory.DONTKNOW).content("blah blah")
                        .tags(Arrays.asList("tag1", "tag2")).disclosure(false).build();

        postsService.createPosts(memberId, request);

        assertThat(spyPostsRepository.save_entity.getId()).isEqualTo("postId");
        assertThat(spyPostsRepository.save_entity.getMemberId()).isEqualTo(1L);
        assertThat(spyPostsRepository.save_entity.getFirstCategory()).isEqualTo(FirstCategory.SADNESS);
        assertThat(spyPostsRepository.save_entity.getSecondCategory()).isEqualTo(SecondCategory.DONTKNOW);
        assertThat(spyPostsRepository.save_entity.getContent()).isEqualTo("blah blah");
        assertThat(spyPostsRepository.save_entity.getTags()).isEqualTo(Arrays.asList("tag1", "tag2"));
        assertThat(spyPostsRepository.save_entity.isDisclosure()).isFalse();
        assertThat(spyPostsRepository.save_entity.getViews()).isZero();
    }

    @Test
    void updatePosts_PostsNotFoundException() {
        assertThatThrownBy(() -> postsService.updatePosts("0", null, null))
                .isInstanceOf(PostsNotFoundException.class);
    }

    @Test
    void updatePosts_AccessDeniedException() {
        spyPostsRepository.findById_returnValue = Posts.builder()
                .memberId(1L)
                .id("1")
                .build();

        assertThatThrownBy(() -> postsService.updatePosts("1", null, 2L))
                .isInstanceOf(AccessDeniedException.class);
    }

    @Test
    void deletePosts_AccessDeniedException() {
        spyPostsRepository.findById_returnValue = Posts.builder()
                .memberId(1L)
                .id("1")
                .build();

        List<String> postIds = List.of("1", "2");
        assertThatThrownBy(() -> postsService.deletePosts(postIds, 2L))
                .isInstanceOf(AccessDeniedException.class);
    }

    @Test
    void createPosts_returnCreatePostsResponse() {
        Long memberId = 1L;
        CreatePostsRequest request =
                CreatePostsRequest.builder().firstCategory(FirstCategory.SADNESS)
                        .secondCategory(SecondCategory.DONTKNOW).content("blah blah")
                        .tags(Arrays.asList("tag1", "tag2")).disclosure(false).build();

        CreatePostsResult result = postsService.createPosts(memberId, request);

        assertThat(spyPostsRepository.save_entity.getId()).isEqualTo(result.getPostId());
    }

    @Test
    void getPostsByMemberId_passesMemberIdToRepository() {
        postsService.getPostsByMemberId(1L, PageRequest.ofSize(1));

        assertThat(spyPostsRepository.findByMemberId_argumentId).isEqualTo(1L);
    }

    @Test
    void getPostsByMemberId_returnsPagingPosts() {
        spyPostsRepository.findByMemberId_returnValue = new PageImpl<>(
                List.of(
                        Posts.builder().id("3").build(),
                        Posts.builder().id("4").build()
                ));

        List<PostsDto> result = postsService.getPostsByMemberId(1L, PageRequest.of(2, 2)).getPosts();

        assertThat(result).containsExactly(
                PostsDto.builder().id("3").build(),
                PostsDto.builder().id("4").build()
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
                Posts.builder().id("1").tags(List.of("1")).build(),
                Posts.builder().id("2").tags(List.of("1", "2")).build(),
                Posts.builder().id("3").tags(List.of("2", "3")).build(),
                Posts.builder().id("4").tags(List.of("1", "2", "3")).build()
        );

        List<PostsDto> result = postsService.getPostsByTag("3", 0, 2);

        assertThat(result).containsExactly(
                PostsDto.builder().id("3").tags(List.of("2", "3")).build(),
                PostsDto.builder().id("4").tags(List.of("1", "2", "3")).build()
        );
    }

    @Test
    void getPostsByTag_returnsPagingPosts() {
        spyPostsRepository.findAll_returnValue = List.of(
                Posts.builder().id("1").tags(List.of("1")).build(),
                Posts.builder().id("2").tags(List.of("1", "2")).build(),
                Posts.builder().id("3").tags(List.of("2", "3")).build(),
                Posts.builder().id("4").tags(List.of("1", "2", "3")).build()
        );

        List<PostsDto> result = postsService.getPostsByTag("3", 1, 1);

        assertThat(result).containsExactly(
                PostsDto.builder().id("4").tags(List.of("1", "2", "3")).build()
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
                Posts.builder().id("1").views(1).build(),
                Posts.builder().id("2").views(10).build(),
                Posts.builder().id("3").views(2).build(),
                Posts.builder().id("4").views(5).build()
        );

        List<PostsDto> result = postsService.getPostsOrderByPopular(0, 4);

        assertThat(result).containsExactly(
                PostsDto.builder().id("2").views(10).build(),
                PostsDto.builder().id("4").views(5).build(),
                PostsDto.builder().id("3").views(2).build(),
                PostsDto.builder().id("1").views(1).build()
        );
    }

    @Test
    void getPostsOrderByPopular_returnsPagingPosts() {
        spyPostsRepository.findAll_returnValue = List.of(
                Posts.builder().id("1").views(1).build(),
                Posts.builder().id("2").views(10).build(),
                Posts.builder().id("3").views(2).build(),
                Posts.builder().id("4").views(5).build()
        );

        List<PostsDto> result = postsService.getPostsOrderByPopular(2, 2);

        assertThat(result).containsExactly(
                PostsDto.builder().id("3").views(2).build(),
                PostsDto.builder().id("1").views(1).build()
        );
    }

    @Test
    void getRecentlyUnwrittenPosts_passesMemberIdToRepository() {
        spyPostsRepository.findByMemberId_returnValue = new PageImpl<>(List.of(
                Posts.builder().id("3").secondCategory(SecondCategory.Unwritten).createdAt(LocalDateTime.now()).build()
        ));

        postsService.getRecentlyUnwrittenPosts(1L);

        assertThat(spyPostsRepository.findByMemberId_argumentId).isEqualTo(1L);
        assertThat(spyPostsRepository.findByMemberId_argumentPageable.getPageNumber()).isEqualTo(0);
        assertThat(spyPostsRepository.findByMemberId_argumentPageable.getPageSize()).isEqualTo(Integer.MAX_VALUE);
    }

    @Test
    void getRecentlyUnwrittenPosts_returnsPagingPosts() {
        LocalDateTime now = LocalDateTime.now().withHour(0).withMinute(0).withSecond(0).withNano(0);

        LocalDateTime before7Day = now.minusDays(6).minusNanos(1);
        LocalDateTime before8Day = now.minusDays(7).minusNanos(1);

        spyPostsRepository.findByMemberId_returnValue = new PageImpl<>(List.of(
                Posts.builder().id("1").secondCategory(SecondCategory.SADNESS).build(),
                Posts.builder().id("3").secondCategory(SecondCategory.DONTKNOW).createdAt(before7Day).build(),
                Posts.builder().id("4").secondCategory(SecondCategory.DONTKNOW).createdAt(before8Day).build()
        ));

        List<PostsDto> result = postsService.getRecentlyUnwrittenPosts(1L);

        assertThat(result).containsExactly(
                PostsDto.builder().id("3").secondCategory(SecondCategory.DONTKNOW).createdAt(before7Day).build());
    }

    @Test
    public void increaseViews() {
        spyPostsRepository.findById_returnValue = Posts.builder().views(0).build();

        postsService.increaseViews("1");

        assertThat(spyPostsRepository.findById_returnValue.getViews()).isEqualTo(1);
    }

    @Test
    void getPostsById_throwException() {
        assertThatThrownBy(() -> postsService.getPostsById(0L, "1")).isInstanceOf(PostsNotFoundException.class);
    }

    @Test
    void getAllPosts__callsFindAllInRepository() {
        postsService.getAllPosts();

        assertThat(spyPostsRepository.findAll_wasCalled).isTrue();
    }

    @Test
    void getAllPosts_returnAllPosts() {
        spyPostsRepository.findAll_returnValue = List.of(
                Posts.builder().id("1").build(),
                Posts.builder().id("2").build(),
                Posts.builder().id("3").build(),
                Posts.builder().id("4").build()
        );

        List<PostsDto> result = postsService.getAllPosts();

        assertThat(result).containsExactly(
                PostsDto.builder().id("1").build(),
                PostsDto.builder().id("2").build(),
                PostsDto.builder().id("3").build(),
                PostsDto.builder().id("4").build()
        );
    }

    @Test
    public void getCategories_returnsCategoryResponse() throws Exception {
        spyPostsRepository.findByMemberId_returnValue = new PageImpl<>(List.of(
                Posts.builder().id("1").firstCategory(FirstCategory.DONTKNOW).secondCategory(SecondCategory.SADNESS).build(),
                Posts.builder().id("2").firstCategory(FirstCategory.ANXIOUS).secondCategory(SecondCategory.DONTKNOW).build(),
                Posts.builder().id("3").firstCategory(FirstCategory.DONTKNOW).secondCategory(SecondCategory.JOY).build(),
                Posts.builder().id("4").firstCategory(FirstCategory.SADNESS).secondCategory(SecondCategory.JOY).build()
        ));

        List<CategoryResponse> result = postsService.getCategories(1L);

        assertThat(result).hasSize(12);

        assertThat(result.get(0)).isEqualTo(new CategoryResponse(2, SecondCategory.JOY));
        assertThat(result.get(1)).isEqualTo(new CategoryResponse(0, SecondCategory.PROUD));
        assertThat(result.get(2)).isEqualTo(new CategoryResponse(0, SecondCategory.RELIEF));
        assertThat(result.get(3)).isEqualTo(new CategoryResponse(0, SecondCategory.EASYGOING));
        assertThat(result.get(4)).isEqualTo(new CategoryResponse(0, SecondCategory.CALMDOWN));
        assertThat(result.get(5)).isEqualTo(new CategoryResponse(0, SecondCategory.LETHARGY));
        assertThat(result.get(6)).isEqualTo(new CategoryResponse(0, SecondCategory.DISAPPOINTMENT));
        assertThat(result.get(7)).isEqualTo(new CategoryResponse(2, SecondCategory.SADNESS));
        assertThat(result.get(8)).isEqualTo(new CategoryResponse(0, SecondCategory.REGRET));
        assertThat(result.get(9)).isEqualTo(new CategoryResponse(0, SecondCategory.IRRITATION));
        assertThat(result.get(10)).isEqualTo(new CategoryResponse(1, SecondCategory.ANXIOUS));
        assertThat(result.get(11)).isEqualTo(new CategoryResponse(3, SecondCategory.DONTKNOW));
    }

    @Test
    public void getCategoryItems_returnsCategoryItemsResponse() throws Exception {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime yesterday = LocalDateTime.now().minusDays(1);
        LocalDateTime tomorrow = LocalDateTime.now().plusDays(1);

        spyPostsRepository.findByMemberId_returnValue = new PageImpl<>(List.of(
                Posts.builder().id("1").views(1).firstCategory(FirstCategory.DONTKNOW).secondCategory(SecondCategory.SADNESS).createdAt(now).build(),
                Posts.builder().id("2").views(1).firstCategory(FirstCategory.ANXIOUS).secondCategory(SecondCategory.DONTKNOW).createdAt(tomorrow).build(),
                Posts.builder().id("3").views(1).firstCategory(FirstCategory.DONTKNOW).secondCategory(SecondCategory.JOY).createdAt(yesterday).build(),
                Posts.builder().id("4").views(1).firstCategory(FirstCategory.SADNESS).secondCategory(SecondCategory.JOY).createdAt(now).build()
        ));

        CategoryItemsResponse result = postsService.getCategoryItems(1L, 12, PageRequest.of(0, 2));

        assertThat(result.getTotalCount()).isEqualTo(3);

        assertThat(result.getPosts()).contains(
                CategoryItemDTO.builder()
                        .postId("2")
                        .firstCategory(FirstCategory.ANXIOUS)
                        .secondCategory(SecondCategory.DONTKNOW)
                        .createdAt(tomorrow)
                        .views(1)
                        .build(),
                CategoryItemDTO.builder()
                        .postId("1")
                        .firstCategory(FirstCategory.DONTKNOW)
                        .secondCategory(SecondCategory.SADNESS)
                        .createdAt(now)
                        .views(1)
                        .build());
    }
}