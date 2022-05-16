package depromeet.ohgzoo.iam.posts;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

public class SpyPostsService implements PostsService {
    public CreatePostsRequest createPosts_argumentRequest;
    public CreatePostsResult createPosts_returnValue;
    public UpdatePostsRequest updatePostsRequest_argumentRequest;
    public Long getPostsByMemberId_argumentMemberId;
    public List<PostsDto> getPostsByMemberId_returnValue;
    public int getPostsByMemberId_argumentPage;
    public int getPostsByMemberId_argumentSize;
    public List<PostsDto> getPostsByTag_returnValue;
    public String getPostsByTag_argumentTag;
    public int getPostsByTag_argumentPage;
    public int getPostsByTag_argumentSize;
    public int getPostsOrderByPopular_argumentPage;
    public int getPostsOrderByPopular_argumentSize;
    public List<PostsDto> getPostsOrderByPopular_returnValue;
    public Long getRecentlyUnwrittenPosts_argumentMemberId;
    public List<PostsDto> getRecentlyUnwrittenPosts_returnValue;
    public String increaseViews_argumentPostId;
    public String getPostsById_argumentId;
    public PostsDto getPostsById_returnValue;
    public int getAllPosts_argumentPage;
    public int getAllPosts_argumentSize;
    public List<PostsDto> getAllPosts_returnValue;
    public Long createPosts_argumentMemberId;
    public Long deletePosts_argumentMemberId;
    public List<String> deletePosts_argumentPostsId;
    public List<CategoryResponse> categories_returnValue;
    public Long categories_argumentMemberId;
    public Long getCategoryItems_argumentMemberId;
    public Integer getCategoryItems_argumentCategoryId;
    public Pageable getCategoryItems_argumentPageable;
    public CategoryItemsResponse getCategoryItems_returnValue;


    @Override
    public CreatePostsResult createPosts(Long memberId, CreatePostsRequest request) {
        createPosts_argumentMemberId = memberId;
        createPosts_argumentRequest = request;
        return createPosts_returnValue;
    }

    @Override
    public void updatePosts(String postId, UpdatePostsRequest request, Long memberId) {
        updatePostsRequest_argumentRequest = request;
    }

    @Override
    public List<PostsDto> getPostsByMemberId(Long memberId, int page, int size) {
        getPostsByMemberId_argumentMemberId = memberId;
        getPostsByMemberId_argumentPage = page;
        getPostsByMemberId_argumentSize = size;
        return getPostsByMemberId_returnValue;
    }

    @Override
    public List<PostsDto> getPostsByTag(String tag, int page, int size) {
        getPostsByTag_argumentTag = tag;
        getPostsByTag_argumentPage = page;
        getPostsByTag_argumentSize = size;

        return getPostsByTag_returnValue;
    }

    @Override
    public List<PostsDto> getPostsOrderByPopular(int page, int size) {
        getPostsOrderByPopular_argumentPage = page;
        getPostsOrderByPopular_argumentSize = size;
        return getPostsOrderByPopular_returnValue;
    }

    @Override
    public List<PostsDto> getRecentlyUnwrittenPosts(Long memberId) {
        getRecentlyUnwrittenPosts_argumentMemberId = memberId;
        return getRecentlyUnwrittenPosts_returnValue;
    }

    @Override
    public void increaseViews(String postId) {
        increaseViews_argumentPostId = postId;
    }

    @Override
    public PostsDto getPostsById(String postId) {
        getPostsById_argumentId = postId;
        return getPostsById_returnValue;
    }

    @Override
    public List<PostsDto> getAllPosts() {
        return getAllPosts_returnValue;
    }

    @Override
    public List<CategoryResponse> getCategories(Long memberId) {
        this.categories_argumentMemberId = memberId;
        return this.categories_returnValue;
    }

    @Override
    public CategoryItemsResponse getCategoryItems(Long memberId, Integer categoryId, Pageable pageable) {
        this.getCategoryItems_argumentMemberId = memberId;
        this.getCategoryItems_argumentCategoryId = categoryId;
        this.getCategoryItems_argumentPageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize());

        return this.getCategoryItems_returnValue;
    }

    @Override
    public void deletePosts(List<String> postIds, Long memberId) {
        deletePosts_argumentMemberId = memberId;
        deletePosts_argumentPostsId = postIds;
    }
}