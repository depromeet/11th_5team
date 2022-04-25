package depromeet.ohgzoo.iam.folder.folderItem;

import depromeet.ohgzoo.iam.folder.FirstCategory;
import depromeet.ohgzoo.iam.folder.Folder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FolderItemServiceImpl implements FolderItemService {

    private final FolderItemRepository folderItemRepository;

    public void createFolderItem(Long memberId, Folder folder, FolderItemCreateRequest request) {
        FolderItem folderItem = new FolderItem(request.getFirstCategory(), request.getSecondCategory(), request.getContent(), request.getTags(), request.getDisclosure());
        folderItemRepository.save(folderItem);

        folder.addFolderItem(folderItem);
        folder.changeCoverImg(folderItem.getFirstCategory());
    }

    @Override
    public void moveFolderItem(Long memberId, Folder folder, FolderItemMoveRequest request) {
        // 폴더 이동
        FolderItem folderItem = folderItemRepository.findById(request.getFolderItemId())
                .orElseThrow(NotExistsFolderItemException::new);

        Folder oldFolder = folderItem.getFolder();
        oldFolder.removeFolderItem(folderItem);

        folder.addFolderItem(folderItem);

        // coverImg 갱신
        changeFolderCoverImage(oldFolder);
        changeFolderCoverImage(folder);
    }

    private void changeFolderCoverImage(Folder folder) {
        FolderItem folderItem = folderItemRepository.findFirstByFolderOrderByCreatedAtDesc(folder);
        folder.changeCoverImg((folderItem == null) ? FirstCategory.DEFAULT : folderItem.getFirstCategory());
    }
}
