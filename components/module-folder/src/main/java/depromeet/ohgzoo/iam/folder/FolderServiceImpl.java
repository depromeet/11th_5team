package depromeet.ohgzoo.iam.folder;

import depromeet.ohgzoo.iam.folder.folderItem.FolderItem;
import depromeet.ohgzoo.iam.folder.folderItem.FolderItemCreateRequest;
import depromeet.ohgzoo.iam.folder.folderItem.FolderItemMoveRequest;
import depromeet.ohgzoo.iam.folder.folderItem.FolderItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FolderServiceImpl implements FolderService {
    private final FolderRepository folderRepository;
    private final FolderItemService folderItemService;

    public FolderResponse createFolder(Long memberId, FolderCreateRequest request) {
        Optional<Folder> existedFolder = folderRepository.findByName(request.getFolderName());
        if (existedFolder.isPresent()) throw new ExistedNameException();

        Folder folder = new Folder(request.getFolderName(), "", memberId);
        folderRepository.save(folder);

        return new FolderResponse(folder.getId(), folder.getName());
    }

    public void deleteFolder(Long memberId, Long folderId) {

        Folder folder = folderRepository.findById(folderId).orElseThrow(NotExistsFolderException::new);
        if (folder.getMemberId() != memberId) throw new InvalidUserException();

        folderRepository.deleteById(folderId);
    }

    public FolderResponse updateFolder(Long memberId, Long folderId, FolderUpdateRequest request) {
        Folder folder = folderRepository.findById(folderId)
                .orElseThrow(NotExistsFolderException::new);

        Optional<Folder> existedFolder = folderRepository.findByName(request.getFolderName());
        if (existedFolder.isPresent()) throw new ExistedNameException();
        if (folder.getMemberId() != memberId) throw new InvalidUserException();

        folder.updateName(request.getFolderName());
        return new FolderResponse(folder.getId(), folder.getName());
    }

    @Override
    public void createFolderItem(Long memberId, Long folderId, FolderItemCreateRequest request) {
        Folder folder = folderRepository.findById(folderId)
                .orElseThrow(NotExistsFolderException::new);

        folderItemService.createFolderItem(memberId, folder, request);
    }

    @Override
    public void moveFolderItem(Long memberId, Long folderId, FolderItemMoveRequest request) {
        Folder newFolder = folderRepository.findById(folderId)
                .orElseThrow(NotExistsFolderException::new);

        folderItemService.moveFolderItem(memberId, newFolder, request);
    }

    @Override
    public FoldersGetResponse getFolders(Long memberId) {
        List<Folder> folders = folderRepository.findAllByMemberId(memberId);
        List<FolderItem> folderItems = folderItemService.getRecentFolderItems(memberId);

        List<String> coverImages = new ArrayList<String>(Arrays.asList(CoverImageUrl.defaultImage, CoverImageUrl.defaultImage, CoverImageUrl.defaultImage, CoverImageUrl.defaultImage));
        for (int i = 0; i < folderItems.size(); i++) {
            if (folderItems.get(i) != null)
                coverImages.set(i, CoverImageUrl.returnCoverImage(folderItems.get(i).getFirstCategory()));
        }

        return new FoldersGetResponse(folders.stream()
                .map(FolderGetResponse::of)
                .collect(Collectors.toList()), coverImages);
    }
}