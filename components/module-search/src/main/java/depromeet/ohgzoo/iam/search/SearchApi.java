package depromeet.ohgzoo.iam.search;

import depromeet.ohgzoo.iam.jwt.Login;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/search")
public class SearchApi {
    private final SearchService searchService;

    @GetMapping
    public SearchResult search(@RequestParam String keyword, @Login Long memberId) {
        return searchService.search(keyword, memberId);
    }
}