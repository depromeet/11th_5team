package depromeet.ohgzoo.iam.folder;

import depromeet.ohgzoo.iam.jwt.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FolderServiceImpl implements FolderService {
    private final JwtService jwtService;
    private final FolderRepository folderRepository;


    public FolderResponse createFolder(String authToken, FolderCreateRequest request) {
        Long userId = Long.valueOf(jwtService.getSubject(authToken));
        Optional<Folder> existedFolder = folderRepository.findByName(request.getFolderName());
        if (existedFolder.isPresent()) throw new ExistedNameException();

        Folder folder = new Folder(request.getFolderName(), "", userId);
        folderRepository.save(folder);

        return new FolderResponse(folder.getId(), folder.getName());
    }

    public void deleteFolder(String authToken, Long folderId) {
        Long userId = Long.valueOf(jwtService.getSubject(authToken));

        Folder folder = folderRepository.findById(folderId).orElseThrow(NotExistsFolderException::new);
        if (folder.getMemberId() != userId) throw new InvalidUserException();

        folderRepository.deleteById(folderId);
    }

    public FolderResponse updateFolder(String authToken, Long folderId, UpdateFolderRequest request) {
        Long userId = Long.valueOf(jwtService.getSubject(authToken));
        Folder folder = folderRepository.findById(folderId)
                .orElseThrow(NotExistsFolderException::new);

        Optional<Folder> existedFolder = folderRepository.findByName(request.getFolderName());
        if (existedFolder.isPresent()) throw new ExistedNameException();
        if (folder.getMemberId() != userId) throw new InvalidUserException();

        folder.updateName(request.getFolderName());
        return new FolderResponse(folder.getId(), folder.getName());
    }


}
