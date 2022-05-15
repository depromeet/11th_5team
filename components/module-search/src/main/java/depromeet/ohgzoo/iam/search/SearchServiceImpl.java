package depromeet.ohgzoo.iam.search;

import depromeet.ohgzoo.iam.search.SearchResult.SearchModel;
import depromeet.ohgzoo.iam.search.batch.SearchEntity;
import depromeet.ohgzoo.iam.search.batch.SearchRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;

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
    public SearchResult searchByTag(String keyword, Long memberId, String order) {
        SearchModel[] filtered = searchRepository.findAll()
                .stream()
                .filter(entity -> entity.getTags().contains(keyword))
                .map(entity -> mapToSearchModel(entity, memberId))
                .sorted(getOrderer(order))
                .toArray(SearchModel[]::new);

        return SearchResult.of(filtered);
    }

    private Comparator<SearchModel> getOrderer(String order) {
        if ("new".equals(order)) {
            return Comparator.comparing(SearchModel::getCreatedAt).reversed();
        } else {
            return Comparator.comparing(SearchModel::getViews).reversed();
        }
    }

    @Override
    public SearchResult searchByCategory(String keyword, Long memberId) {
        SearchModel[] filtered = searchRepository.findAll()
                .stream()
                .filter(entity -> containsCategory(keyword, entity))
                .map(entity -> mapToSearchModel(entity, memberId))
                .toArray(SearchModel[]::new);

        return SearchResult.of(filtered);
    }

    private boolean containsCategory(String keyword, SearchEntity entity) {
        return entity.getFirstCategory().contains(keyword)
                || entity.getSecondCategory().contains(keyword);
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
