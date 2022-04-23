package depromeet.ohgzoo.iam.posts;

import depromeet.ohgzoo.iam.jwt.Login;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/posts")
public class PostsApi {

    private final PostsService postsService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CreatePostsResult createPosts(@Login Long memberId, @RequestBody CreatePostsRequest request) {
        return postsService.createPosts(memberId, request);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Page<PostsDto> findAllPostsOfMe(@Login Long memberId, @PageableDefault(size = 20) Pageable pageable) {
        return postsService.findAllPostsOfMe(memberId, pageable);
    }

    @GetMapping("/search")
    @ResponseStatus(HttpStatus.OK)
    public Page<PostsDto> findPostsByTag(@RequestParam String tag, @PageableDefault(size = 20) Pageable pageable) {
        return postsService.findPostsByTag(tag, pageable);
    }

    @GetMapping("/popular")
    @ResponseStatus(HttpStatus.OK)
    public Page<PostsDto> findPostsOrderByPopular(@PageableDefault(size = 20) Pageable pageable) {
        return postsService.findPostsOrderByPopular(pageable);
    }

    @GetMapping("/second")
    @ResponseStatus(HttpStatus.OK)
    public PostsDto findRecentPostWhereSecondCategoryIsNull(@Login Long memberId) {
        return postsService.findRecentPostWhereSecondCategoryIsNull(memberId);
    }
}
