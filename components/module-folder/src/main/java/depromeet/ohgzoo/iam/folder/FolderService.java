package depromeet.ohgzoo.iam.folder;

import depromeet.ohgzoo.iam.folder.folderItem.FolderItemCreateRequest;
import depromeet.ohgzoo.iam.folder.folderItem.FolderItemMoveRequest;
import depromeet.ohgzoo.iam.folder.folderItem.FolderItemsGetResponse;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface FolderService {
    FolderResponse createFolder(Long memberId, FolderCreateRequest request);

    void deleteFolder(Long memberId, Long folderId);

    FolderResponse updateFolder(Long memberId, Long folderId, FolderUpdateRequest request);

    FoldersGetResponse getFolders(Long memberId);

    void createFolderItem(Long memberId, Long folderId, FolderItemCreateRequest request);

    void moveFolderItem(Long memberId, Long folderId, FolderItemMoveRequest request);

    FolderItemsGetResponse getFolderItems(Long memberId, Long folderId, Pageable pageable);

    void deleteFolderItems(Long memberId, List<String> postIds);

    void createDefaultFolder(Long memberId);
}
