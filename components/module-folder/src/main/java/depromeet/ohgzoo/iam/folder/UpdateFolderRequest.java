package depromeet.ohgzoo.iam.folder;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UpdateFolderRequest {
    private String name;

    public UpdateFolderRequest(String name) {
        this.name = name;
    }
}

