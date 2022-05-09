package depromeet.ohgzoo.iam.search;

public interface SearchService {
    SearchResult search(String keyword, Long memberId);

    SearchResult searchByTag(String keyword, Long memberId);
}
