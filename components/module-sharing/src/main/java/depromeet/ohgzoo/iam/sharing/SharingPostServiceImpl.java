package depromeet.ohgzoo.iam.sharing;

import depromeet.ohgzoo.iam.member.Member;
import depromeet.ohgzoo.iam.member.MemberNotExistsException;
import depromeet.ohgzoo.iam.member.MemberRepository;
import depromeet.ohgzoo.iam.posts.Posts;
import depromeet.ohgzoo.iam.posts.PostsNotFoundException;
import depromeet.ohgzoo.iam.posts.PostsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;

@Service
@RequiredArgsConstructor
public class SharingPostServiceImpl implements SharingPostService {
    private final SharingPostRepository sharingPostRepository;
    private final PostsRepository postsRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public SharingResponse sharingPost(Long memberId, SharingRequest request) {
        if(postsRepository.findById(request.getPostId()).isEmpty()) throw new PostsNotFoundException();

        SharingPost sharingPost = SharingPost.builder()
                .receiverName(request.getReceiverName()).sharingCategory(request.getCategory()).postId(request.getPostId())
                .memberId(memberId).build();
        sharingPostRepository.save(sharingPost);

        String content = sharingPost.getId() + "," + memberId + "," + request.getPostId(); // 굳이?
        String link = getBase64EncodeString(content);

        return SharingResponse.builder().link(link).build();
    }

    @Transactional(readOnly = true)
    public SharingPostDto getSharingPost(String link) {
        String content = getBase64DecodeString(link);
        String[] split = content.split(",");

        SharingPost sharingPost = sharingPostRepository.findById(Long.valueOf(split[0]))
                .orElseThrow(SharingPostNotFoundException::new);
        Posts posts = postsRepository.findById(sharingPost.getPostId()).orElseThrow(PostsNotFoundException::new);
        Member member = memberRepository.findById(sharingPost.getMemberId()).orElseThrow(MemberNotExistsException::new);

        if(!posts.getMemberId().equals(member.getId())) throw new PostsNotFoundException();

        return SharingPostDto.builder()
                .receiverName(sharingPost.getReceiverName()).category(sharingPost.getSharingCategory())
                .content(posts.getContent()).senderName(member.getNickname()).build();
    }

    private static String getBase64EncodeString(String content) {
        return Base64Utils.encodeToString(content.getBytes());
    }

    private static String getBase64DecodeString(String content) {
        return new String(Base64Utils.decode(content.getBytes()));
    }
}
