package depromeet.ohgzoo.iam.folder;

import depromeet.ohgzoo.iam.folder.folderItem.FolderItemCreateRequest;
import depromeet.ohgzoo.iam.folder.folderItem.FolderItemMoveRequest;

public class SpyFolderService implements FolderService {
    public Long argument_memberId;
    public Long updateFolder_argumentMemberId;
    public Long updateFolder_argumentFolderId;
    public Long deleteFolder_argumentId;
    public Long deleteFolderItem_argumentPostId;
    public FolderResponse createFolder_returnValue;
    public FolderResponse updateFolder_returnValue;
    public FolderCreateRequest createFolder_argumentRequest;
    public FolderUpdateRequest updateFolder_argumentRequest;
    public FolderItemCreateRequest createFolderItem_argumentRequest;
    public FolderItemMoveRequest moveFolderItem_argumentRequest;


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
        createFolderItem_argumentRequest = request;
    }

    @Override
    public void moveFolderItem(Long memberId, Long folderId, FolderItemMoveRequest request) {
        moveFolderItem_argumentRequest = request;
    }

    @Override
    public void deleteFolderItem(Long memberId, Long postId) {
        deleteFolderItem_argumentPostId = postId;
    }
}
