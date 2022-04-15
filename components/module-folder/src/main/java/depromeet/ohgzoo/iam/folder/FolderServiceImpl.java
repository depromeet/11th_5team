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

    public CreateFolderResponse createFolder(String authToken, String name) {
        String userId = jwtService.getSubject(authToken);
        Long longValue = Long.valueOf(userId);

        Folder folder = new Folder(name, "", longValue);
        folderRepository.save(folder);

        return new CreateFolderResponse(folder.getId(),folder.getName());
    }
}
