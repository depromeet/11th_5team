package depromeet.ohgzoo.iam.search.batch;

import depromeet.ohgzoo.iam.ListToStringConverter;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SearchEntity {
    @Id
    private String id;
    private Long memberId;
    private String firstCategory;
    private String secondCategory;
    private String content;
    @Convert(converter = ListToStringConverter.class)
    private List<String> tags = new ArrayList<>();
    private int views;
    private LocalDateTime createdAt;

    @Builder
    public SearchEntity(String id, Long memberId, String firstCategory, String secondCategory, String content, List<String> tags, int views, LocalDateTime createdAt) {
        this.id = id;
        this.memberId = memberId;
        this.firstCategory = firstCategory;
        this.secondCategory = secondCategory;
        this.content = content;
        this.tags = tags;
        this.views = views;
        this.createdAt = createdAt;
    }
}
