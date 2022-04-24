package depromeet.ohgzoo.iam.folder.folderItem;

import depromeet.ohgzoo.iam.folder.Folder;
import depromeet.ohgzoo.iam.folder.FolderItemMoveRequest;
import depromeet.ohgzoo.iam.folder.FolderItemRepository;
import depromeet.ohgzoo.iam.folder.FolderRepository;
import depromeet.ohgzoo.iam.folder.NotExistsFolderException;
import depromeet.ohgzoo.iam.folder.NotExistsFolderItemException;
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

    public void moveFolderItem(Long memberId, Long folderId, FolderItemMoveRequest request) {
        // 폴더 이동
        FolderItem folderItem = folderItemRepository.findById(request.getFolderItemId())
                .orElseThrow(NotExistsFolderItemException::new);
        Folder oldFolder = folderItem.getFolder();
        oldFolder.removeFolderItem(folderItem);
        Folder newFolder = folderRepository.findById(folderId)
                .orElseThrow(NotExistsFolderException::new);
        newFolder.addFolderItem(folderItem);

        // coverImg 갱신
//        folderRepository.findLastestFolderItem(folderId);
    }
}
