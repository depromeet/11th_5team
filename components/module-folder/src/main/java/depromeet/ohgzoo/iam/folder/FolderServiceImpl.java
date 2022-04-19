package depromeet.ohgzoo.iam.folder;

import depromeet.ohgzoo.iam.jwt.JwtService;
import depromeet.ohgzoo.iam.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FolderServiceImpl implements FolderService {
    private final JwtService jwtService;
    private final FolderRepository folderRepository;

    public FolderResponse createFolder(String authToken, FolderCreateRequest request) {
        Long userId = Long.valueOf(jwtService.getSubject(authToken));

        Folder folder = new Folder(request.getFolderName(), "", userId);
        folderRepository.save(folder);

        return new FolderResponse(folder.getId(), folder.getName());
    }

    public void deleteFolder(String authToken, Long folderId) {
        Long userId = Long.valueOf(jwtService.getSubject(authToken));

        Folder folder = folderRepository.findById(folderId).get();
        if (folder.getMemberId() != userId) throw new InvalidUserException();

        folderRepository.deleteById(folderId);
    }

    public FolderResponse updateFolder(String authToken, Long folderId, UpdateFolderRequest request) {
        Long userId = Long.valueOf(jwtService.getSubject(authToken));

        Folder folder = folderRepository.findById(folderId)
                .orElseThrow(NotExistsFolderException::new);

        if (folder.getMemberId() != userId) throw new InvalidUserException();

        folder.updateName(request.getName());

        return new FolderResponse(folder.getId(), folder.getName());
    }
}
