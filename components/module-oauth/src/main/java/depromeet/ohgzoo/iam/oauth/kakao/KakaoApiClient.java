package depromeet.ohgzoo.iam.oauth.kakao;

import depromeet.ohgzoo.iam.FeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "kakaoApi", url = "https://kapi.kakao.com", configuration = FeignConfig.FeignConfiguration.class)
public interface KakaoApiClient {
    @PostMapping(value = "/v2/user/me", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    String getProfile(@RequestHeader("Authorization") String authorization);
}
