package depromeet.ohgzoo.iam.search.batch;


import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
public class RemotePosts {
    private String id;
    private Long memberId;
    private String firstCategory;
    private String secondCategory;
    private String content;
    private List<String> tags;
    private int views;

    public RemotePosts(String id, Long memberId, String firstCategory, String secondCategory, String content, List<String> tags, int views) {
        this.id = id;
        this.memberId = memberId;
        this.firstCategory = firstCategory;
        this.secondCategory = secondCategory;
        this.content = content;
        this.tags = tags;
        this.views = views;
    }
}
