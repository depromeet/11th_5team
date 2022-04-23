package depromeet.ohgzoo.iam.posts;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PostsService {
    CreatePostsResult createPosts(Long memberId, CreatePostsRequest request);
    Page<PostsDto> findAllPostsOfMe(Long memberId, Pageable pageable);
    Page<PostsDto> findPostsByTag(String tag, Pageable pageable);
    Page<PostsDto> findPostsOrderByPopular(Pageable pageable);
    PostsDto findRecentPostWhereSecondCategoryIsNull(Long memberId);
}
