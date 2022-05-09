package depromeet.ohgzoo.iam.search.batch;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;
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
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;
}
