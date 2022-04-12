package depromeet.ohgzoo.iam.oauth;

import depromeet.ohgzoo.iam.jwt.JwtService;
import depromeet.ohgzoo.iam.member.MemberJoinRequest;
import depromeet.ohgzoo.iam.member.MemberService;
import depromeet.ohgzoo.iam.oauth.kakao.KakaoClient;
import depromeet.ohgzoo.iam.oauth.kakao.KakaoProfileResponse;
import depromeet.ohgzoo.iam.oauth.kakao.KakaoProfileResponse.KakaoAccount;
import depromeet.ohgzoo.iam.oauth.kakao.KakaoTokenRequest;
import depromeet.ohgzoo.iam.oauth.kakao.KakaoTokenResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class OauthServiceImpl implements OauthService {
    @Value("${kakaoClientId}")
    private String clientId;

    @Value("${kakaoRedirectUrl}")
    private String redirectUrl;

    private final KakaoClient kakaoClient;
    private final JwtService jwtService;
    private final MemberService memberService;

    @Override
    public Oauth2LoginUrl getLoginUrl() {
        return new Oauth2LoginUrl(
                new StringBuilder("https://kauth.kakao.com/oauth/authorize")
                        .append("?client_id=").append(clientId)
                        .append("&response_type=code")
                        .append("&redirect_uri=").append(redirectUrl)
                        .toString()
        );
    }

    @Override
    public AuthToken getToken(String code) {
        KakaoTokenResponse kakaoToken = getKakaoToken(code);
        KakaoProfileResponse profile = getKakaoProfile(kakaoToken);

        String email = profile.getAccount().getEmail();
        if (!memberService.alreadyJoin(email)) {
            memberService.join(mapToMemberJoinRequest(profile));
        }
        
        Long memberId = memberService.getMemberId(email);

        return new AuthToken(
                jwtService.issuedToken(memberId.toString(), "USER", 3600),
                jwtService.issuedToken(memberId.toString(), "USER", 36000)
        );
    }

    private MemberJoinRequest mapToMemberJoinRequest(KakaoProfileResponse profile) {
        KakaoAccount account = profile.getAccount();
        return new MemberJoinRequest("", account.getProfile().getNickname(), account.getEmail());
    }

    private KakaoProfileResponse getKakaoProfile(KakaoTokenResponse kakaoToken) {
        return kakaoClient.getProfile("Bearer " + kakaoToken.getAccessToken());
    }

    private KakaoTokenResponse getKakaoToken(String code) {
        KakaoTokenRequest kakaoTokenRequest = new KakaoTokenRequest(clientId, redirectUrl, code);
        return kakaoClient.getToken(kakaoTokenRequest);
    }
}

