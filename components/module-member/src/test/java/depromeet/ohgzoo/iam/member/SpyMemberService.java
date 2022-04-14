package depromeet.ohgzoo.iam.member;

public class SpyMemberService implements MemberService {
    public String getMySelf_argumentToken;
    public MemberResponse getMySelf_returnValue;

    @Override
    public boolean alreadyJoin(String identifyToken) {
        return false;
    }

    @Override
    public void join(MemberJoinRequest request) {

    }

    @Override
    public Long getMemberId(String identifyToken) {
        return null;
    }

    @Override
    public MemberResponse getMySelf(String token) {
        getMySelf_argumentToken = token;
        return getMySelf_returnValue;
    }
}