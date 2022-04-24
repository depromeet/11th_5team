package depromeet.ohgzoo.iam.folder;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FolderServiceImpl implements FolderService {
    private final FolderRepository folderRepository;


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
}