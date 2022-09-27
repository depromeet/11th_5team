package depromeet.ohgzoo.iam.oauth.apple;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ApplePublicKeyResponse {
    private List<Key> keys;

    public Key getMatchedPublicKey(AppleHeader header) {
        return keys.stream()
                .filter(key -> header.getKid().equals(key.getKid()) && header.getAlg().equals(key.getAlg()))
                .findFirst()
                .orElseThrow(IllegalStateException::new);
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class Key {
        private String alg;
        private String e;
        private String kid;
        private String kty;
        private String n;
        private String use;
    }
}
