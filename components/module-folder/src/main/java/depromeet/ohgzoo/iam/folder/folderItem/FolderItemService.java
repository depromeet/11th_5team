package depromeet.ohgzoo.iam.folder.folderItem;

import depromeet.ohgzoo.iam.folder.Folder;

public interface FolderItemService {
    void createFolderItem(Long memberId, Folder folder, FolderItemCreateRequest request);

    void moveFolderItem(Long memberId, Folder folder, FolderItemMoveRequest request);

    void deleteFolderItem(Long memberId, Long postId);
}
