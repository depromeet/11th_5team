package depromeet.ohgzoo.iam.oauth.kakao;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class KakaoProfileResponse {
    @JsonProperty("id")
    private Long id;
    @JsonProperty("kakao_account")
    private KakaoAccount account;

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class KakaoAccount {
        @JsonProperty("profile")
        private KakaoProfile profile;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class KakaoProfile {
        @JsonProperty("nickname")
        private String nickname;
        @JsonProperty("profile_image_url")
        private String profileImg;
    }
}