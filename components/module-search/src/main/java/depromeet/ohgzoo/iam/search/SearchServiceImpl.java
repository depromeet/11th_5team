package depromeet.ohgzoo.iam.search;

import depromeet.ohgzoo.iam.search.SearchResult.SearchModel;
import depromeet.ohgzoo.iam.search.batch.SearchEntity;
import depromeet.ohgzoo.iam.search.batch.SearchRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class SearchServiceImpl implements SearchService {
    private final SearchRepository searchRepository;

    @Override
    public SearchResult search(String keyword, Long memberId) {
        SearchModel[] filtered = searchRepository.findAll()
                .stream()
                .filter(entity -> entity.getContent().contains(keyword))
                .map(entity -> mapToSearchModel(entity, memberId))
                .toArray(SearchModel[]::new);

        return SearchResult.of(filtered);
    }

    @Override
    public SearchResult searchByTag(String keyword, Long memberId) {
        SearchModel[] filtered = searchRepository.findAll()
                .stream()
                .filter(entity -> entity.getTags().contains(keyword))
                .map(entity -> mapToSearchModel(entity, memberId))
                .toArray(SearchModel[]::new);

        return SearchResult.of(filtered);
    }

    private SearchModel mapToSearchModel(SearchEntity post, Long memberId) {
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
