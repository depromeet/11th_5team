package depromeet.ohgzoo.iam.search;

import depromeet.ohgzoo.iam.search.SearchResult.SearchModel;
import depromeet.ohgzoo.iam.search.batch.SearchEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class SearchServiceImplTest {

    private SearchService sut;
    private SpySearchRepository spyPostRepository;

    @BeforeEach
    void setUp() {
        spyPostRepository = new SpySearchRepository();
        sut = new SearchServiceImpl(spyPostRepository);
    }

    @Test
    void search_callsFindAllInRepository() {
        sut.search("keyword", null);

        assertThat(spyPostRepository.findAll_wasCalled).isTrue();
    }

    @Test
    void search_returnsContainedKeywordSearchResult() {
        spyPostRepository.findAll_returnValue = List.of(
                new SearchEntity("1", 1L, "", "", "hi", Collections.emptyList(), 0, LocalDateTime.now()),
                new SearchEntity("2", 1L, "", "", "hello", Collections.emptyList(), 0, LocalDateTime.now())
        );

        SearchResult result = sut.search("hi", null);

        assertThat(result.getPosts()).hasSize(1);

        SearchModel actual = result.getPosts().get(0);
        assertThat(actual.getContent()).isEqualTo("hi");
        assertThat(actual.getId()).isEqualTo("1");
        assertThat(actual.isMy()).isFalse();
    }

    @Test
    void search_returnsContainedKeywordSearchResult_whenLogin() {

        spyPostRepository.findAll_returnValue = List.of(
                new SearchEntity("1", 1L, "", "", "hi", Collections.emptyList(), 0, LocalDateTime.now()),
                new SearchEntity("2", 2L, "", "", "hi hi", Collections.emptyList(), 0, LocalDateTime.now()),
                new SearchEntity("3", 1L, "", "", "hello", Collections.emptyList(), 0, LocalDateTime.now())
        );

        SearchResult result = sut.search("hi", 1L);

        assertThat(result.getPosts()).hasSize(2);

        assertThat(result.getPosts().get(0).getContent()).isEqualTo("hi");
        assertThat(result.getPosts().get(0).getId()).isEqualTo("1");
        assertThat(result.getPosts().get(0).isMy()).isTrue();

        assertThat(result.getPosts().get(1).getContent()).isEqualTo("hi hi");
        assertThat(result.getPosts().get(1).getId()).isEqualTo("2");
        assertThat(result.getPosts().get(1).isMy()).isFalse();
    }

    @Test
    void searchByTag_callsFindAllInRepository() {
        sut.searchByTag("keyword", null, null);

        assertThat(spyPostRepository.findAll_wasCalled).isTrue();
    }

    @Test
    void searchByTag_returnsContainedTagSearchResult_orderByViews() {
        spyPostRepository.findAll_returnValue = List.of(
                new SearchEntity("1", 1L, "", "", "content1", List.of("hi"), 0, LocalDateTime.now()),
                new SearchEntity("2", 1L, "", "", "content2", List.of("hi"), 1, LocalDateTime.now()),
                new SearchEntity("3", 1L, "", "", "content3", List.of("hello"), 0, LocalDateTime.now())
        );

        SearchResult result = sut.searchByTag("hi", null, null);

        assertThat(result.getPosts()).hasSize(2);
        assertThat(result.getPosts().get(0).getId()).isEqualTo("2");
        assertThat(result.getPosts().get(0).getViews()).isEqualTo(1);
        assertThat(result.getPosts().get(1).getId()).isEqualTo("1");
        assertThat(result.getPosts().get(1).getViews()).isEqualTo(0);
    }

    @Test
    void searchByTag_returnsContainedTagSearchResult_orderByCreatedAt() {
        LocalDateTime givenNow = LocalDateTime.now();
        spyPostRepository.findAll_returnValue = List.of(
                new SearchEntity("1", 1L, "", "", "content1", List.of("hi"), 0, givenNow.minusDays(1)),
                new SearchEntity("2", 1L, "", "", "content2", List.of("hi"), 0, givenNow)
        );

        SearchResult result = sut.searchByTag("hi", null, "new");

        assertThat(result.getPosts()).hasSize(2);
        assertThat(result.getPosts().get(0).getId()).isEqualTo("2");
        assertThat(result.getPosts().get(0).getCreatedAt()).isEqualTo(givenNow);
        assertThat(result.getPosts().get(1).getId()).isEqualTo("1");
        assertThat(result.getPosts().get(1).getCreatedAt()).isEqualTo(givenNow.minusDays(1));
    }

    @Test
    void searchByTag_returnsContainedTagSearchResult_whenLogin() {

        spyPostRepository.findAll_returnValue = List.of(
                new SearchEntity("1", 1L, "", "", "content1", List.of("hi"), 0, LocalDateTime.now()),
                new SearchEntity("2", 2L, "", "", "content2", List.of("hi", "hello"), 0, LocalDateTime.now()),
                new SearchEntity("3", 1L, "", "", "content3", List.of("hello"), 0, LocalDateTime.now())
        );

        SearchResult result = sut.searchByTag("hi", 1L, null );

        assertThat(result.getPosts()).hasSize(2);

        assertThat(result.getPosts().get(0).getTags()).contains("hi");
        assertThat(result.getPosts().get(0).getId()).isEqualTo("1");
        assertThat(result.getPosts().get(0).isMy()).isTrue();

        assertThat(result.getPosts().get(1).getTags()).contains("hi", "hello");
        assertThat(result.getPosts().get(1).getId()).isEqualTo("2");
        assertThat(result.getPosts().get(1).isMy()).isFalse();
    }

    @Test
    void searchByCategory_callsFindAllInRepository() {
        sut.searchByCategory("keyword", null);

        assertThat(spyPostRepository.findAll_wasCalled).isTrue();
    }

    @Test
    void searchByCategory_returnsSearchResult_containsFirstCategory() {
        spyPostRepository.findAll_returnValue = List.of(
                new SearchEntity("1", 1L, "hi", "", "content1", List.of("tag1"), 0, LocalDateTime.now()),
                new SearchEntity("2", 1L, "", "", "content2", List.of("tag2"), 0, LocalDateTime.now())
        );

        SearchResult result = sut.searchByCategory("hi", null);

        assertThat(result.getPosts()).hasSize(1);

        SearchModel actual = result.getPosts().get(0);
        assertThat(actual.getFirstCategory()).isEqualTo("hi");
        assertThat(actual.getId()).isEqualTo("1");
        assertThat(actual.isMy()).isFalse();
    }

    @Test
    void searchByCategory_returnsSearchResult_containsSecondCategory() {
        spyPostRepository.findAll_returnValue = List.of(
                new SearchEntity("1", 1L, "", "", "content1", List.of("tag1"), 0, LocalDateTime.now()),
                new SearchEntity("2", 1L, "", "hi", "content2", List.of("tag2"), 0, LocalDateTime.now())
        );

        SearchResult result = sut.searchByCategory("hi", null);

        assertThat(result.getPosts()).hasSize(1);

        SearchModel actual = result.getPosts().get(0);
        assertThat(actual.getSecondCategory()).isEqualTo("hi");
        assertThat(actual.getId()).isEqualTo("2");
        assertThat(actual.isMy()).isFalse();
    }

    @Test
    void getRankingTag_callsFindAll() {
        sut.getRankingTag();

        assertThat(spyPostRepository.findAll_wasCalled).isTrue();
    }

    @Test
    void getRankingTag_returnsTagRanks_orderByTagFrequency() {
        spyPostRepository.findAll_returnValue = List.of(
                new SearchEntity("1", 1L, "", "", "", List.of("tag1"), 0, LocalDateTime.now()),
                new SearchEntity("2", 1L, "", "", "", List.of("tag1", "tag2"), 0, LocalDateTime.now()),
                new SearchEntity("3", 1L, "", "", "", List.of("tag1", "tag2", "tag3"), 0, LocalDateTime.now())
        );

        List<TagRank> result = sut.getRankingTag();

        assertThat(result).hasSize(3);
        assertThat(result.get(0).getTag()).isEqualTo("tag1");
        assertThat(result.get(0).getFrequency()).isEqualTo(3);
        assertThat(result.get(1).getTag()).isEqualTo("tag2");
        assertThat(result.get(1).getFrequency()).isEqualTo(2);
        assertThat(result.get(2).getTag()).isEqualTo("tag3");
        assertThat(result.get(2).getFrequency()).isEqualTo(1);
    }

    @Test
    void getRankingTag_returnsEmptyList_whenNotExistsPosts() {
        spyPostRepository.findAll_returnValue = List.of();

        List<TagRank> result = sut.getRankingTag();

        assertThat(result).isEmpty();
    }

    @Test
    void getRankingTag_excludeEmptyTag() {
        spyPostRepository.findAll_returnValue = List.of(
                new SearchEntity("1", 1L, "", "", "", List.of(""), 0, LocalDateTime.now())
        );

        List<TagRank> result = sut.getRankingTag();

        assertThat(result).hasSize(0);
    }
}