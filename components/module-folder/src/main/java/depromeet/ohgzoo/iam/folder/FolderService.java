package depromeet.ohgzoo.iam.folder;

import depromeet.ohgzoo.iam.folder.folderItem.FolderItemCreateRequest;
import depromeet.ohgzoo.iam.folder.folderItem.FolderItemMoveRequest;

import java.util.List;

public interface FolderService {
    FolderResponse createFolder(Long memberId, FolderCreateRequest request);

    void deleteFolder(Long memberId, Long folderId);

    FolderResponse updateFolder(Long memberId, Long folderId, FolderUpdateRequest request);

    void createFolderItem(Long memberId, Long folderId, FolderItemCreateRequest request);

    void moveFolderItem(Long memberId, Long folderId, FolderItemMoveRequest request);

    List<FolderGetResponse> getFolders(Long memberId);
}
