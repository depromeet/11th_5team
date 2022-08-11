package depromeet.ohgzoo.iam.search.batch;

import depromeet.ohgzoo.iam.BaseEntity;
import depromeet.ohgzoo.iam.DecryptConverter;
import depromeet.ohgzoo.iam.ListToStringConverter;
import depromeet.ohgzoo.iam.category.FirstCategory;
import depromeet.ohgzoo.iam.category.SecondCategory;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SearchEntity extends BaseEntity {
    @Id
    private String id;
    private Long memberId;
    @Enumerated(EnumType.STRING)
    private FirstCategory firstCategory;
    @Enumerated(EnumType.STRING)
    private SecondCategory secondCategory;
    @Convert(converter = DecryptConverter.class)
    private String content;
    @Convert(converter = ListToStringConverter.class)
    private List<String> tags = new ArrayList<>();
    private int views;

    @Builder
    public SearchEntity(String id, Long memberId, FirstCategory firstCategory, SecondCategory secondCategory, String content, List<String> tags, int views) {
        this.id = id;
        this.memberId = memberId;
        this.firstCategory = firstCategory;
        this.secondCategory = secondCategory;
        this.content = content;
        this.tags = tags;
        this.views = views;
    }

    public void update(UpdatePostRequest request) {
        if (!memberId.equals(request.getMemberId())) {
            throw new IllegalStateException("[memberId] is must be equals");
        }

        this.secondCategory = request.getSecondCategory();
        this.content = request.getContent();
        this.tags = request.getTags();
    }

    public void increaseViews() {
        views++;
    }
}
