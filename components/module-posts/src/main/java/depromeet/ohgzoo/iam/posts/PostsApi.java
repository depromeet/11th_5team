package depromeet.ohgzoo.iam.posts;

import depromeet.ohgzoo.iam.jwt.Login;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/posts")
public class PostsApi {

    private final PostsService postsService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public CreatePostsResult createPosts(@RequestBody CreatePostsRequest request) {
        return postsService.createPosts(request);
    }

    @PatchMapping("/{postid}")
    public void updatePosts(
            @PathVariable("postid") Long postId,
            @RequestBody UpdatePostsRequest request,
            @Login Long memberId) {
        postsService.updatePosts(postId, request, memberId);
    }

    @DeleteMapping
    public void deletePosts(
            @RequestParam List<Long> postIds,
            @Login Long memberId) {
        postsService.deletePosts(postIds, memberId);
    }

}