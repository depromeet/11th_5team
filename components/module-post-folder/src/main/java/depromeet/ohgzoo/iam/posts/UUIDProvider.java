package depromeet.ohgzoo.iam.posts;

import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class UUIDProvider {

    public UUID randomUUID() {
        return UUID.randomUUID();
    }
}
