package depromeet.ohgzoo.iam.member;

public interface MemberService {
    boolean alreadyJoin(String identifyToken);

    void join(MemberJoinRequest request);

    Long getMemberId(String identifyToken);

    MemberResponse getMySelf(String token);

    void updateMember(UpdateMemberRequest request, Long memberId);
}
