package depromeet.ohgzoo.iam.posts;

import depromeet.ohgzoo.iam.jwt.Login;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

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
    public List<PostsDto> getMyPosts(@Login Long memberId,
                                     @RequestParam(defaultValue = "0") int page,
                                     @RequestParam(defaultValue = "20") int size) {
        return postsService.getPostsByMemberId(memberId, page, size);
    }

    @GetMapping("/search")
    public List<PostsDto> getPostsByTag(@RequestParam String tag,
                                        @RequestParam(defaultValue = "0") int page,
                                        @RequestParam(defaultValue = "20") int size) {
        return postsService.getPostsByTag(tag, page, size);
    }

    @GetMapping("/popular")
    public List<PostsDto> getPostsOrderByPopular(@RequestParam(defaultValue = "0") int page,
                                                 @RequestParam(defaultValue = "20") int size) {
        return postsService.getPostsOrderByPopular(page, size);
    }

    @GetMapping("/temp")
    public PostsDto getRecentlyUnwrittenPosts(@Login Long memberId) {
        return postsService.getRecentlyUnwrittenPosts(memberId);
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

    @PatchMapping("/{postid}/views")
    public void increaseViews(
            @PathVariable("postid") Long postId) {
        postsService.increaseViews(postId);
    }

    @GetMapping("/{postId}")
    public PostsDto getPostsById(@PathVariable Long postId) {
        return postsService.getPostsById(postId);
    }

    @GetMapping("/all")
    public List<PostsDto> getAllPosts() {
        return postsService.getAllPosts();
    }
}