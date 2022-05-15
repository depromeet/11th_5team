package depromeet.ohgzoo.iam.search;

import depromeet.ohgzoo.iam.search.SearchResult.SearchModel;

import java.util.Collections;
import java.util.List;

public class SpySearchService implements SearchService {
    public String search_argumentKeyword;
    public SearchModel search_returnValue;
    public Long search_argumentMemberId;
    public List<SearchModel> searchByTag_returnValue = Collections.emptyList();
    public String searchByTag_argumentKeyword;
    public Long searchByTag_argumentMemberId;
    public SearchModel searchByCategory_returnValue;
    public String searchByCategory_argumentKeyword;
    public Long searchByCategory_argumentMemberId;
    public String searchByTag_argumentOrder;

    @Override
    public SearchResult search(String keyword, Long memberId) {
        search_argumentKeyword = keyword;
        search_argumentMemberId = memberId;
        return SearchResult.of(search_returnValue);
    }

    @Override
    public SearchResult searchByTag(String keyword, Long memberId, String order) {
        searchByTag_argumentKeyword = keyword;
        searchByTag_argumentMemberId = memberId;
        searchByTag_argumentOrder = order;
        return SearchResult.of(searchByTag_returnValue.toArray(SearchModel[]::new));
    }

    @Override
    public SearchResult searchByCategory(String keyword, Long memberId) {
        searchByCategory_argumentKeyword = keyword;
        searchByCategory_argumentMemberId = memberId;
        return SearchResult.of(searchByCategory_returnValue);
    }
}
