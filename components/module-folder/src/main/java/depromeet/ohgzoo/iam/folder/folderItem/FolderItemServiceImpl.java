package depromeet.ohgzoo.iam.folder.folderItem;

import depromeet.ohgzoo.iam.folder.Folder;
import depromeet.ohgzoo.iam.folder.FolderRepository;
import depromeet.ohgzoo.iam.folder.NotExistsFolderException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FolderItemServiceImpl implements FolderItemService{

    private final FolderItemRepository folderItemRepository;
    private final FolderRepository folderRepository;

    public void createFolderItem(Long memberId, Long folderId, FolderItemCreateRequest request) {
        Folder folder = folderRepository.findById(folderId)
                .orElseThrow(NotExistsFolderException::new);

        FolderItem folderItem = new FolderItem(request.getFirstCategory(), request.getSecondCategory(), request.getContent(), request.getTags(), request.getDisclosure());
        folderItemRepository.save(folderItem);
        folder.addFolderItem(folderItem);
        folder.changeCoverImg(folderItem.getFirstCategory());
    }
}
