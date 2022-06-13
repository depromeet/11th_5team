package depromeet.ohgzoo.iam.search.batch;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class UpdatePostRequest {
    private Long memberId;
    private String secondCategory;
    private String content;
    private List<String> tags = new ArrayList<>();

    public UpdatePostRequest(Long memberId, String secondCategory, String content, List<String> tags) {
        this.memberId = memberId;
        this.secondCategory = secondCategory;
        this.content = content;
        this.tags.addAll(tags);
    }
}
