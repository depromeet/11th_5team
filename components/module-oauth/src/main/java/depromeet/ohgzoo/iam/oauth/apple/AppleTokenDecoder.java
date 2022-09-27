package depromeet.ohgzoo.iam.oauth.apple;

public interface AppleTokenDecoder {
    AppleProfile getProfile(String code);
}
