package depromeet.ohgzoo.iam.folder;

import depromeet.ohgzoo.iam.jwt.Login;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class FolderApi {

    private final FolderService folderService;

    @PostMapping("/api/v1/folders")
    public FolderResponse addFolder(@Login Long memberId, @RequestBody FolderCreateRequest request) {
        if (request.getFolderName() == null) throw new NullValueException();

        FolderResponse response = folderService.createFolder(memberId, request);
        return response;
    }

    @DeleteMapping("/api/v1/folders/{folderId}")
    public void deleteFolder(@Login Long memberId,
                             @PathVariable Long folderId) {
        folderService.deleteFolder(memberId, folderId);
    }

    @PatchMapping("/api/v1/folders/{folderId}")
    public FolderResponse updateFolder(@Login Long memberId,
                                       @PathVariable Long folderId,
                                       @RequestBody UpdateFolderRequest request) {

        return folderService.updateFolder(memberId, folderId, request);
    }
}
