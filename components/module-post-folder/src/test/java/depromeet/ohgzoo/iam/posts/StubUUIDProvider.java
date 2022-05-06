package depromeet.ohgzoo.iam.posts;

import java.util.UUID;

public class StubUUIDProvider extends UUIDProvider {
    public UUID randomUUID_returnValue;

    @Override
    public UUID randomUUID() {
        return randomUUID_returnValue;
    }
}
