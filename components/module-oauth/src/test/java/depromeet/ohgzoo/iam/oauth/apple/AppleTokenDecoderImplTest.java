package depromeet.ohgzoo.iam.oauth.apple;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Map;

class AppleTokenDecoderImplTest {

    @Test
    void name() throws JsonProcessingException {

        ObjectMapper objectMapper = new ObjectMapper();
        String token = "eyJraWQiOiJZdXlYb1kiLCJhbGciOiJSUzI1NiJ9.eyJpc3MiOiJodHRwczovL2FwcGxlaWQuYXBwbGUuY29tIiwiYXVkIjoia3IubW9vZHBpYyIsImV4cCI6MTY2Mjg3NzA5NywiaWF0IjoxNjYyNzkwNjk3LCJzdWIiOiIwMDE5NDcuYmMxMTA1MDQ4NTM1NGI3ZjgyZWRjNDA2M2YzYjlkNTUuMTUwNyIsImNfaGFzaCI6IjFHMzB2T2ItYXNGRl9UVkVjZzNDLXciLCJlbWFpbCI6Ijh5ejJ4dG1nc25AcHJpdmF0ZXJlbGF5LmFwcGxlaWQuY29tIiwiZW1haWxfdmVyaWZpZWQiOiJ0cnVlIiwiaXNfcHJpdmF0ZV9lbWFpbCI6InRydWUiLCJhdXRoX3RpbWUiOjE2NjI3OTA2OTcsIm5vbmNlX3N1cHBvcnRlZCI6dHJ1ZX0.s35grRho8OEekhImbAKWTZraaCs4KeLMqiUryg3rziDYH6UAKFbkYGq14Sgt3nA8TIIL9OtBjsv67xcsDmvB1WoPxIDc7bfBGkf_wH4AtAPeXJeX5DnEgzeLKlbF0sQl0oaA2konFipHqZkR806NelhUt70CMcxV46T5YatBpyMON5WzawElctaWLbddbXYSdXw7hQPUDUgWpe7eqqKNPBBc3yI6Ky7N3v-nb7LD-sJNqUCbZp26zP75Yqw4LOJ84aLdiP-7LLX_D7aK3izLSlgADDVFdF70OO4lVcJdgfe1hZshvFfkkAYP24K8JWZHDRG1a98rBQZChNattsVubg";
        String headerToken = token.split("\\.")[0];

        Map<String, String> header = objectMapper.readValue(new String(Base64.getDecoder().decode(headerToken), StandardCharsets.UTF_8), new TypeReference<>() {

        });

        System.out.println(headerToken);
        System.out.println(header);
    }
}