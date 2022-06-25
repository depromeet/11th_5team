package depromeet.ohgzoo.iam.folder;

import depromeet.ohgzoo.iam.folder.folderItem.FolderItemCreateRequest;
import depromeet.ohgzoo.iam.folder.folderItem.FolderItemMoveRequest;
import depromeet.ohgzoo.iam.folder.folderItem.FolderItemUpdateRequest;
import depromeet.ohgzoo.iam.folder.folderItem.FolderItemsGetResponse;
import org.springframework.data.domain.Pageable;

import java.util.List;

public class SpyFolderService implements FolderService {
    public Long argument_memberId;
    public Long updateFolder_argumentMemberId;
    public Long updateFolder_argumentFolderId;
    public Long deleteFolder_argumentId;
    public List<String> deleteFolderItems_argumentPostIds;
    public FolderResponse createFolder_returnValue;
    public FolderResponse updateFolder_returnValue;
    public FolderCreateRequest createFolder_argumentRequest;
    public FolderUpdateRequest updateFolder_argumentRequest;
    public FolderItemCreateRequest createFolderItem_argumentRequest;
    public FolderItemMoveRequest moveFolderItem_argumentRequest;
    public FolderItemUpdateRequest updateFolderItem_argumentRequest;
    public String updateFolderItem_argumentPostId;
    public FoldersGetResponse getFolders_returnValue;
    public FolderItemsGetResponse getFolderItems_returnValue;
    public Long createFolderItem_argumentMemberId;
    public Long createFolderItem_argumentFolderId;
    public String increaseViews_argumentPostId;
    public String getFolderByPost_argumentPostId;
    public FolderGetResponse getFolderByPost_returnValue;
    public Long deleteFolders_argumentMemberId;

    @Override
    public FolderResponse createFolder(Long memberId, FolderCreateRequest request) {
        argument_memberId = memberId;
        createFolder_argumentRequest = request;
        return createFolder_returnValue;
    }

    @Override
    public void deleteFolder(Long memberId, Long id) {
        deleteFolder_argumentId = id;
        argument_memberId = memberId;
    }

    @Override
    public FolderResponse updateFolder(Long memberId, Long id, FolderUpdateRequest request) {
        updateFolder_argumentMemberId = memberId;
        updateFolder_argumentFolderId = id;
        updateFolder_argumentRequest = request;
        return updateFolder_returnValue;
    }

    @Override
    public void createFolderItem(Long memberId, Long folderId, FolderItemCreateRequest request) {
        createFolderItem_argumentMemberId = memberId;
        createFolderItem_argumentFolderId = folderId;
        createFolderItem_argumentRequest = request;
    }

    @Override
    public void moveFolderItem(Long memberId, Long folderId, FolderItemMoveRequest request) {
        moveFolderItem_argumentRequest = request;
    }

    @Override
    public FolderItemsGetResponse getFolderItems(Long memberId, Long folderId, Pageable pageable) {
        return getFolderItems_returnValue;
    }

    @Override
    public FoldersGetResponse getFolders(Long memberId) {
        argument_memberId = memberId;
        return getFolders_returnValue;
    }

    @Override
    public void deleteFolderItems(Long memberId, List<String> postIds) {
        argument_memberId = memberId;
        deleteFolderItems_argumentPostIds = postIds;
    }

    @Override
    public void createDefaultFolder(Long memberId) {
        argument_memberId = memberId;
    }

    @Override
    public void increaseViews(String postId) {
        increaseViews_argumentPostId = postId;
    }

    @Override
    public void updateFolderItem(Long memberId, String postId, FolderItemUpdateRequest request) {
        argument_memberId = memberId;
        updateFolderItem_argumentPostId = postId;
        updateFolderItem_argumentRequest = request;
    }

    @Override
    public FolderGetResponse getFolderByPost(String postId) {
        getFolderByPost_argumentPostId = postId;
        return getFolderByPost_returnValue;
    }

    @Override
    public void deleteAllFolders(Long memberId) {
        this.deleteFolders_argumentMemberId = memberId;
    }
}
