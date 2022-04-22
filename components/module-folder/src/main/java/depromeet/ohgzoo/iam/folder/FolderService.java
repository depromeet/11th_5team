package depromeet.ohgzoo.iam.folder;


public interface FolderService {
    FolderResponse createFolder(Long memberId, FolderCreateRequest request);

    void deleteFolder(Long memberId, Long folderId);

    FolderResponse updateFolder(Long memberId, Long folderId, UpdateFolderRequest request);
}
