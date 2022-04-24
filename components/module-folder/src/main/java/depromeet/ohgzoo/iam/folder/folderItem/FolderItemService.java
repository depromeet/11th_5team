package depromeet.ohgzoo.iam.folder.folderItem;

import depromeet.ohgzoo.iam.folder.FolderItemMoveRequest;

public interface FolderItemService {
    void createFolderItem(Long memberId, Long folderId, FolderItemCreateRequest request);
    void moveFolderItem(Long memberId,Long folderId, FolderItemMoveRequest request);
}
