package depromeet.ohgzoo.iam.folder;

import com.sun.istack.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class FolderUpdateRequest {
    @NotNull
    private String folderName;

    public FolderUpdateRequest(String folderName) {
        this.folderName = folderName;
    }
}

