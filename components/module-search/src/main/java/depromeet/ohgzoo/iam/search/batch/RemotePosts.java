package depromeet.ohgzoo.iam.search.batch;


import depromeet.ohgzoo.iam.category.FirstCategory;
import depromeet.ohgzoo.iam.category.SecondCategory;
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
    private FirstCategory firstCategory;
    private SecondCategory secondCategory;
    private String content;
    private List<String> tags;
    private int views;

    public RemotePosts(String id, Long memberId, FirstCategory firstCategory, SecondCategory secondCategory, String content, List<String> tags, int views) {
        this.id = id;
        this.memberId = memberId;
        this.firstCategory = firstCategory;
        this.secondCategory = secondCategory;
        this.content = content;
        this.tags = tags;
        this.views = views;
    }
}
