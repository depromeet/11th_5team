package depromeet.ohgzoo.iam.member;

import lombok.Getter;

@Getter
public class MemberJoinRequest {
    private String profileImg;
    private String nickname;
    private String identifyToken;

    public MemberJoinRequest(String profileImg, String nickname, String identifyToken) {
        this.profileImg = profileImg;
        this.nickname = nickname;
        this.identifyToken = identifyToken;
    }
}
