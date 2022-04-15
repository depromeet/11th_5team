package depromeet.ohgzoo.iam.folder;


public interface FolderService {
    CreateFolderResponse createFolder(String authToken, String name);
}
