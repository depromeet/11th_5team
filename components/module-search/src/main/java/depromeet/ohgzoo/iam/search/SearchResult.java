package depromeet.ohgzoo.iam.search;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Getter
public class SearchResult {
    private List<SearchModel> posts = new ArrayList<>();

    private SearchResult(List<SearchModel> searchModels) {
        posts.addAll(searchModels);
    }

    public static SearchResult of(SearchModel... searchModel) {
        return new SearchResult(Arrays.asList(searchModel));
    }

    @Getter
    static class SearchModel {
        private String id;
        private String firstCategory;
        private String secondCategory;
        private String content;
        private List<String> tags;
        private int views;
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        private LocalDateTime createdAt;
        private boolean my;

        public SearchModel(String id, String firstCategory, String secondCategory, String content, List<String> tags, int views, LocalDateTime createdAt, boolean my) {
            this.id = id;
            this.firstCategory = firstCategory;
            this.secondCategory = secondCategory;
            this.content = content;
            this.tags = tags;
            this.views = views;
            this.createdAt = createdAt;
            this.my = my;
        }
    }
}