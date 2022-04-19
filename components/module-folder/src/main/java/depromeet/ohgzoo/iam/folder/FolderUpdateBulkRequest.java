package depromeet.ohgzoo.iam.folder;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class FolderUpdateBulkRequest {
    private List<FolderUpdateUnitRequest> updated;
    private List<Long> deleted;

    public FolderUpdateBulkRequest(List<FolderUpdateUnitRequest> updated, List<Long> deleted) {
        this.updated = updated;
        this.deleted = deleted;
    }
}

