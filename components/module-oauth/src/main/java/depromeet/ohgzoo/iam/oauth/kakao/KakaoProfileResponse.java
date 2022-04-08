package depromeet.ohgzoo.iam.oauth.kakao;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class KakaoProfileResponse {
    @JsonProperty("id")
    private Long id;
    @JsonProperty("kakao_account")
    private KakaoAccount account;

    @Getter
    @NoArgsConstructor
    private static class KakaoAccount {
        @JsonProperty("email")
        private String email;
        @JsonProperty("profile")
        private KakaoProfile profile;
    }

    @Getter
    @NoArgsConstructor
    private static class KakaoProfile {
        @JsonProperty("nickname")
        private String nickname;
    }
}