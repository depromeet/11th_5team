package depromeet.ohgzoo.iam.folder;

import depromeet.ohgzoo.iam.folder.folderItem.FolderItemCreateRequest;
import depromeet.ohgzoo.iam.folder.folderItem.FolderItemMoveRequest;

public interface FolderService {
    FolderResponse createFolder(Long memberId, FolderCreateRequest request);

    void deleteFolder(Long memberId, Long folderId);

    FolderResponse updateFolder(Long memberId, Long folderId, FolderUpdateRequest request);

    void createFolderItem(Long memberId, Long folderId, FolderItemCreateRequest request);

    void moveFolderItem(Long memberId, Long folderId, FolderItemMoveRequest request);

    FoldersGetResponse getFolders(Long memberId);
}
