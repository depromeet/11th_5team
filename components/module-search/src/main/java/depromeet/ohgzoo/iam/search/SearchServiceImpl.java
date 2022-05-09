package depromeet.ohgzoo.iam.search;

import depromeet.ohgzoo.iam.search.SearchResult.SearchModel;
import depromeet.ohgzoo.iam.search.batch.PostEntity;
import depromeet.ohgzoo.iam.search.batch.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class SearchServiceImpl implements SearchService {
    private final PostRepository postRepository;

    @Override
    public SearchResult search(String keyword, Long memberId) {
        SearchModel[] filtered = postRepository.findAll()
                .stream()
                .filter(entity -> entity.getContent().contains(keyword))
                .map(entity -> mapToSearchModel(entity, memberId))
                .toArray(SearchModel[]::new);

        return SearchResult.of(filtered);
    }

    private SearchModel mapToSearchModel(PostEntity post, Long memberId) {
        return new SearchModel(
                post.getId(),
                post.getFirstCategory(),
                post.getSecondCategory(),
                post.getContent(),
                post.getTags(),
                post.getViews(),
                post.getCreatedAt(),
                post.getMemberId() == memberId
        );
    }
}
