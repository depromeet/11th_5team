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

    private ResultModel(boolean success, int code, String msg, T data) {
        this.success = success;
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public static <T> ResultModel<T> of(T data) {
        return new ResultModel<>(true, 0, "", data);
    }
}
