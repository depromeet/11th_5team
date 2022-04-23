package depromeet.ohgzoo.iam.folder;

import com.sun.istack.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class FolderCreateRequest {
    @NotNull
    private String folderName;

    public FolderCreateRequest(String folderName) {
        this.folderName = folderName;
    }
}
