package depromeet.ohgzoo.iam.oauth;

import depromeet.ohgzoo.iam.FeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "kakao", url = "https://kauth.kakao.com", configuration = FeignConfig.FeignConfiguration.class)
public interface KakaoClient {
    @PostMapping(value = "/oauth/token", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    String getToken(@ModelAttribute KakaoTokenRequest request);
}
