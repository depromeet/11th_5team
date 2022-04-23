package depromeet.ohgzoo.iam.posts;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public class SpyPostsService implements PostsService {
    public CreatePostsRequest createPosts_argumentRequest;
    public CreatePostsResult createPosts_returnValue;

    @Override
    public CreatePostsResult createPosts(Long memberId, CreatePostsRequest request) {
        createPosts_argumentRequest = request;
        return createPosts_returnValue;
    }

    @Override
    public Page<PostsDto> findAllPostsOfMe(Long memberId, Pageable pageable) {
        return null;
    }

    @Override
    public Page<PostsDto> findPostsByTag(String tag, Pageable pageable) {
        return null;
    }

    @Override
    public Page<PostsDto> findPostsOrderByPopular(Pageable pageable) {
        return null;
    }

    @Override
    public PostsDto findRecentPostWhereSecondCategoryIsNull(Long memberId) {
        return null;
    }
}
