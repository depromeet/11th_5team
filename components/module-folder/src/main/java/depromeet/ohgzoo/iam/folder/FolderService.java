package depromeet.ohgzoo.iam.folder;


public interface FolderService {
    FolderResponse createFolder(String authToken, FolderCreateRequest request);

    void deleteFolder(String authToken, Long folderId);

    FolderResponse updateFolder(String authToken, Long folderId, UpdateFolderRequest request);
}
