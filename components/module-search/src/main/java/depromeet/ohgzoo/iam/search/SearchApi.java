package depromeet.ohgzoo.iam.search;

import depromeet.ohgzoo.iam.jwt.Login;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/search")
public class SearchApi {
    private final SearchService searchService;

    @GetMapping
    public SearchResult search(@RequestParam String keyword, @Login Long memberId) {
        return searchService.search(keyword, memberId);
    }

    @GetMapping("tag")
    public SearchResult searchByTag(@RequestParam String keyword, @Login Long memberId,
                                    @RequestParam(required = false) String order) {
        return searchService.searchByTag(keyword, memberId, order);
    }

    @GetMapping("category")
    public SearchResult searchByCategory(@RequestParam String keyword, @Login Long memberId) {
        return searchService.searchByCategory(keyword, memberId);
    }

    @GetMapping("ranking/tag")
    public List<TagRank> getRankingTag() {
        return searchService.getRankingTag();
    }
}
