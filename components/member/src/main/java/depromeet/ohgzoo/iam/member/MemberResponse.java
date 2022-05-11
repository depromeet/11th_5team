package depromeet.ohgzoo.iam.member;

import lombok.Getter;

@Getter
public class MemberResponse {
    private String profileImg;
    private String nickname;

    public MemberResponse(String profileImg, String nickname) {
        this.profileImg = profileImg;
        this.nickname = nickname;
    }
}
