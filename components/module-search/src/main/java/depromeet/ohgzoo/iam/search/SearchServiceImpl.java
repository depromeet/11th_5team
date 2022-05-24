package depromeet.ohgzoo.iam.search;

import depromeet.ohgzoo.iam.search.SearchResult.SearchModel;
import depromeet.ohgzoo.iam.search.batch.SearchEntity;
import depromeet.ohgzoo.iam.search.batch.SearchRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

    @Override
    public List<TagRank> getRankingTag() {
        List<SearchEntity> result = searchRepository.findAll();

        Map<String, TagRank> tagFrequency = getTagFrequency(result);

        return tagFrequency.values()
                .stream()
                .sorted(Comparator.comparing(TagRank::getFrequency).reversed())
                .collect(Collectors.toList());
    }

    private Map<String, TagRank> getTagFrequency(List<SearchEntity> result) {
        Map<String, TagRank> tagFrequency = new HashMap<>();
        for (var item : result) {
            applyTagFrequency(tagFrequency, item);
        }
        return tagFrequency;
    }

    private void applyTagFrequency(Map<String, TagRank> tagFrequency, SearchEntity item) {
        for (var tag : item.getTags()) {
            if (tagFrequency.containsKey(tag)) {
                tagFrequency.get(tag).increase();
            } else {
                tagFrequency.put(tag, new TagRank(tag, 1));
            }
        }
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
