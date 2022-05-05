package depromeet.ohgzoo.iam.search.batch;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ResultModel <T>{
    private boolean success;
    private int code;
    private String msg;
    private T data;
}
