package depromeet.ohgzoo.iam.sharing;

public interface SharingPostService {
    SharingResponse sharingPost(Long memberId, SharingRequest request);
    SharingPostDto getSharingPost(String link);
}
