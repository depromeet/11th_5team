package depromeet.ohgzoo.iam.folder.folderItem;

import depromeet.ohgzoo.iam.folder.Folder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface FolderItemService {
    void createFolderItem(Long memberId, Folder folder, FolderItemCreateRequest request);

    void moveFolderItem(Long memberId, Folder folder, FolderItemMoveRequest request);

    void updateFolderItem(Long memberId, String postId, Folder folder, FolderItemUpdateRequest request);

    void changeFolderCoverImage(Folder folder);

    List<FolderItem> getRecentFolderItems(Long memberId);

    Page<FolderItem> getFolderItemsByFolder(Long memberId, Folder folder, Pageable pageable);

    void deleteFolderItems(Long memberId, List<String> postIds);

    void increaseViews(String postId);

    FolderItem getFolderItemByPostId(String postId);

    void deleteAllFolderItems(Long memberId);

    void encrypt();
}
