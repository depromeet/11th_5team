package depromeet.ohgzoo.iam.folder;

import depromeet.ohgzoo.iam.folder.folderItem.FolderItemCreateRequest;
import depromeet.ohgzoo.iam.folder.folderItem.FolderItemMoveRequest;
import depromeet.ohgzoo.iam.folder.folderItem.FolderItemUpdateRequest;
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

    void deleteAllFolderItems(Long memberId, Long folderId);

    void createDefaultFolder(Long memberId);

    void increaseViews(String postId);

    void updateFolderItem(Long memberId, String postId, FolderItemUpdateRequest request);

    FolderGetResponse getFolderByPost(String postId);

    void deleteAllFolders(Long memberId);
}
