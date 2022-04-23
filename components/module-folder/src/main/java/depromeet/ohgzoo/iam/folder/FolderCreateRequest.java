package depromeet.ohgzoo.iam.folder;


import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class FolderCreateRequest {
    @NotNull
    private String folderName;

    public FolderCreateRequest(String folderName) {
        this.folderName = folderName;
    }
}
