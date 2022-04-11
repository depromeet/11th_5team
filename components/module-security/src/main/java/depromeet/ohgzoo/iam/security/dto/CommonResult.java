package depromeet.ohgzoo.iam.security.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CommonResult {
    private String msg;
    private String code;
}