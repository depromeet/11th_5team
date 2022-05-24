package depromeet.ohgzoo.iam.sharing;

import org.springframework.util.Base64Utils;

public class SpySharingPostService implements SharingPostService {
    public Long sharingPost_argumentMemberId;
    public SharingRequest sharingPost_argumentRequest;
    public SharingResponse sharingPost_returnValue;

    public String getSharingPost_argumentLink;
    public String getSharingPost_sharingPostId;
    public String getSharingPost_postId;
    public String getSharingPost_memberId;
    public SharingPostDto getSharingPost_returnValue;

    @Override
    public SharingResponse sharingPost(Long memberId, SharingRequest request) {
        sharingPost_argumentMemberId = memberId;
        sharingPost_argumentRequest = request;

        return sharingPost_returnValue;
    }

    @Override
    public SharingPostDto getSharingPost(String link) {
        getSharingPost_argumentLink = link;
        String content = new String(Base64Utils.decode(link.getBytes()));
        String[] split = content.split(",");

        getSharingPost_sharingPostId = split[0];
        getSharingPost_postId = split[1];
        getSharingPost_memberId = split[2];

        return getSharingPost_returnValue;
    }
}
