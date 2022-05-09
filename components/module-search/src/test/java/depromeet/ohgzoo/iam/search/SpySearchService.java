package depromeet.ohgzoo.iam.search;

import depromeet.ohgzoo.iam.search.SearchResult.SearchModel;

public class SpySearchService implements SearchService {
    public String search_argumentKeyword;
    public SearchModel search_returnValue;
    public Long search_argumentMemberId;

    @Override
    public SearchResult search(String keyword, Long memberId) {
        search_argumentKeyword = keyword;
        search_argumentMemberId = memberId;
        return SearchResult.of(search_returnValue);
    }
}
