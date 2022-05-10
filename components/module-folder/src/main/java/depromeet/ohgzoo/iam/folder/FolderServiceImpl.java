package depromeet.ohgzoo.iam.folder;

import depromeet.ohgzoo.iam.folder.folderItem.FolderItem;
import depromeet.ohgzoo.iam.folder.folderItem.FolderItemCreateRequest;
import depromeet.ohgzoo.iam.folder.folderItem.FolderItemDto;
import depromeet.ohgzoo.iam.folder.folderItem.FolderItemMoveRequest;
import depromeet.ohgzoo.iam.folder.folderItem.FolderItemService;
import depromeet.ohgzoo.iam.folder.folderItem.FolderItemsGetResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class FolderServiceImpl implements FolderService {
    private final FolderRepository folderRepository;
    private final FolderItemService folderItemService;

    @Override
    public FolderResponse createFolder(Long memberId, FolderCreateRequest request) {
        Optional<Folder> existedFolder = folderRepository.findByName(request.getFolderName());
        if (existedFolder.isPresent()) throw new ExistedNameException();

        Folder folder = new Folder(request.getFolderName(), "", memberId);
        folderRepository.save(folder);

        return new FolderResponse(folder.getId(), folder.getName());
    }

    @Override
    public void deleteFolder(Long memberId, Long folderId) {

        Folder folder = folderRepository.findById(folderId).orElseThrow(NotExistsFolderException::new);
        if (folder.getMemberId() != memberId) throw new InvalidUserException();

        folderRepository.deleteById(folderId);
    }

    @Override
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
    @Transactional(readOnly = true)
    public FoldersGetResponse getFolders(Long memberId) {
        List<Folder> folders = folderRepository.findAllByMemberId(memberId);
        List<FolderItem> folderItems = folderItemService.getRecentFolderItems(memberId);

        List<String> coverImages = Arrays.asList(CoverImageUrl.defaultImage, CoverImageUrl.defaultImage, CoverImageUrl.defaultImage, CoverImageUrl.defaultImage);
        for (int i = 0; i < folderItems.size(); i++) {
            if (folderItems.get(i) != null)
                coverImages.set(i, CoverImageUrl.returnCoverImage(folderItems.get(i).getFirstCategory()));
        }

        return new FoldersGetResponse(folders.stream()
                .map(FolderGetResponse::of)
                .collect(Collectors.toList()), coverImages);
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
    @Transactional(readOnly = true)
    public FolderItemsGetResponse getFolderItems(Long memberId, Long folderId, Pageable pageable) {
        Folder folder = folderRepository.findById(folderId)
                .orElseThrow(NotExistsFolderException::new);

        List<FolderItem> folderItems = folder.getFolderItems()
                .stream()
                .sorted(Comparator.comparing(FolderItem::getCreatedAt).reversed())
                .skip(pageable.getPageNumber())
                .limit(pageable.getPageSize())
                .collect(Collectors.toList());

        return new FolderItemsGetResponse(folder.getFolderItems().size(), folderItems.stream()
                .map(FolderItemDto::of).collect(Collectors.toList()));
    }

    @Override
    public void deleteFolderItems(Long memberId, List<String> postIds) {
        folderItemService.deleteFolderItems(memberId, postIds);
    }
}