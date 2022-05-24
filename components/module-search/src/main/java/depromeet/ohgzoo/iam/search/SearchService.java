package depromeet.ohgzoo.iam.search;

import java.util.List;

public interface SearchService {
    SearchResult search(String keyword, Long memberId);

    SearchResult searchByTag(String keyword, Long memberId, String order);

    SearchResult searchByCategory(String keyword, Long memberId);

    List<TagRank> getRankingTag();
}
