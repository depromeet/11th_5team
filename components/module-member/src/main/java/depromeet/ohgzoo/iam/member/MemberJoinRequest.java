package depromeet.ohgzoo.iam.member;

import lombok.Getter;

@Getter
public class MemberJoinRequest {
    private String profileImg;
    private String nickname;
    private String email;

    public MemberJoinRequest(String profileImg, String nickname, String email) {
        this.profileImg = profileImg;
        this.nickname = nickname;
        this.email = email;
    }
}
