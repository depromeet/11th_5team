package depromeet.ohgzoo.iam.folder;


public interface FolderService {
    FolderResponse createFolder(String authToken, String name);
    void deleteFolder(String authToken,String id);
}
