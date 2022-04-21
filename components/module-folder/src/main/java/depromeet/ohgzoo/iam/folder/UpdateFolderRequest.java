package depromeet.ohgzoo.iam.folder;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UpdateFolderRequest {
    private String folderName;

    public UpdateFolderRequest(String folderName) {
        this.folderName = folderName;
    }
}

