package depromeet.ohgzoo.iam.search;

import depromeet.ohgzoo.iam.search.SearchResult.SearchModel;
import depromeet.ohgzoo.iam.search.batch.PostEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class SearchServiceImplTest {

    private SearchService sut;
    private SpyPostRepository spyPostRepository;

    @BeforeEach
    void setUp() {
        spyPostRepository = new SpyPostRepository();
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
                new PostEntity("1", 1L, "", "", "hi", Collections.emptyList(), 0),
                new PostEntity("2", 1L, "", "", "hello", Collections.emptyList(), 0)
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
                new PostEntity("1", 1L, "", "", "hi", Collections.emptyList(), 0),
                new PostEntity("2", 2L, "", "", "hi hi", Collections.emptyList(), 0),
                new PostEntity("3", 1L, "", "", "hello", Collections.emptyList(), 0)
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
}