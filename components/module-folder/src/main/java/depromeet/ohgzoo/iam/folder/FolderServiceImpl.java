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
    private final MemberRepository memberRepository;

    public FolderResponse createFolder(String authToken, String name) {
        Long userId = Long.valueOf(jwtService.getSubject(authToken));

        Folder folder = new Folder(name, "", userId);
        folderRepository.save(folder);

        return new FolderResponse(folder.getId(), folder.getName());
    }

    public void deleteFolder(String authToken, Long folderId) {
        Long userId = Long.valueOf(jwtService.getSubject(authToken));

        folderRepository.deleteById(folderId);
    }

    public FolderResponse updateFolder(String authToken, Long folderId, UpdateFolderRequest request) {
        Folder folder = folderRepository.findById(folderId)
                .orElseThrow(NotExistsFolderException::new);

        folder.updateName(request.getName());

        return new FolderResponse(folder.getId(), folder.getName());
    }
}
