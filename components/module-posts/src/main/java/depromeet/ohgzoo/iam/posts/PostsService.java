package depromeet.ohgzoo.iam.posts;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PostsService {
    CreatePostsResult createPosts(Long memberId, CreatePostsRequest request);
    List<PostsDto> getPostsByMemberId(Long memberId, int page, int size);
    List<PostsDto> getPostsByTag(String tag, int page, int size);
    Page<PostsDto> findPostsOrderByPopular(Pageable pageable);
    PostsDto findRecentPostWhereSecondCategoryIsNull(Long memberId);
}
