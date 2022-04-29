package depromeet.ohgzoo.iam.folder.folderItem;

import depromeet.ohgzoo.iam.folder.Folder;

import java.util.List;

public interface FolderItemService {
    void createFolderItem(Long memberId, Folder folder, FolderItemCreateRequest request);

    void moveFolderItem(Long memberId, Folder folder, FolderItemMoveRequest request);

    void changeFolderCoverImage(Folder folder);

    List<FolderItem> getRecentFolderItems(Long memberId);
}
