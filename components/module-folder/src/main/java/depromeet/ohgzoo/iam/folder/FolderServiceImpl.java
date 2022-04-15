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

    public void deleteFolder(String authToken,String id) {
        Long userId = Long.valueOf(jwtService.getSubject(authToken));
        Long folderId = Long.valueOf(id);

        folderRepository.deleteById(folderId);
    }
}
