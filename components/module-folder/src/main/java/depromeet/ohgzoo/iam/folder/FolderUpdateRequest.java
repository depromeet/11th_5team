package depromeet.ohgzoo.iam.folder;


import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class FolderUpdateRequest {
    @NotNull
    private String folderName;

    public FolderUpdateRequest(String folderName) {
        this.folderName = folderName;
    }
}

