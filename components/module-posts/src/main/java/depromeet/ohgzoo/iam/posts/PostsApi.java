package depromeet.ohgzoo.iam.posts;

import depromeet.ohgzoo.iam.jwt.Login;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/posts")
public class PostsApi {

    private final PostsService postsService;

    @GetMapping
    public PostsPage getMyPosts(@Login Long memberId,
                                     @PageableDefault(size = 20) Pageable pageable) {
        return postsService.getPostsByMemberId(memberId, pageable);
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
    public List<PostsDto> getRecentlyUnwrittenPosts(@Login Long memberId) {
        return postsService.getRecentlyUnwrittenPosts(memberId);
    }

    @GetMapping("/{postId}")
    public OnePostsDto getPostsById(@Login Long memberId, @PathVariable String postId) {
        return postsService.getPostsById(memberId, postId);
    }

    @GetMapping("/all")
    public List<PostResponse> getAllPosts() {
        return postsService.getAllPosts();
    }

    @GetMapping("/categories")
    public List<CategoryResponse> getCategories(
            @Login Long memberId) {
        return postsService.getCategories(memberId);
    }

    @GetMapping("/categories/{categoryid}")
    public CategoryItemsResponse getCategoryItems(
            @Login Long memberId,
            @PathVariable("categoryid") Integer categoryId,
            @PageableDefault(size = 20) Pageable pageable) {
        return postsService.getCategoryItems(memberId, categoryId, pageable);
    }
}