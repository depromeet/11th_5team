package depromeet.ohgzoo.iam.folder;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class FolderApi {

    private final FolderService folderService;

    @PostMapping("/api/v1/folders")
    public FolderResponse addFolder(@RequestHeader("AUTH_TOKEN") String authToken, @RequestParam String name) {
        if (name == null) throw new NullValueException();

        FolderResponse response = folderService.createFolder(authToken, name);
        return response;
    }

    @DeleteMapping("/api/v1/folders/{folderId}")
    public void deleteFolder(@RequestHeader("AUTH_TOKEN") String authToken,
                             @PathVariable Long folderId) {
        folderService.deleteFolder(authToken, folderId);
    }

    @PatchMapping("/api/v1/folders/{folderId}")
    public FolderResponse updateFolder(@RequestHeader("AUTH_TOKEN") String authToken,
                                       @PathVariable Long folderId,
                                       @RequestBody UpdateFolderRequest request) {

        return folderService.updateFolder(authToken, folderId, request);
    }
}
