package depromeet.ohgzoo.iam.folder;

import depromeet.ohgzoo.iam.folder.exception.NullValueException;
import depromeet.ohgzoo.iam.folder.exception.ValidationException;
import depromeet.ohgzoo.iam.jwt.Login;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
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
    public FolderResponse addFolder(@Login Long memberId, @Validated @RequestBody FolderCreateRequest request, BindingResult errors) {
        if (errors.hasErrors()) throw new ValidationException();

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
                                       @Validated @RequestBody FolderUpdateRequest request, BindingResult errors) {
        if (errors.hasErrors()) throw new ValidationException();
        return folderService.updateFolder(memberId, folderId, request);
    }

    @PostMapping("/api/v1/folders/posts/{folderId}")
    public void addFolderItem(@Login Long memberId, @PathVariable Long folderId, @Validated @RequestBody FolderItemCreateRequest request, BindingResult errors) {
        if (errors.hasErrors()) throw new ValidationException();

        folderService.createFolderItem(memberId, folderId, request);
    }
}
