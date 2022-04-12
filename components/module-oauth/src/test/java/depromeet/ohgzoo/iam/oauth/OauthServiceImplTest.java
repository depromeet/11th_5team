package depromeet.ohgzoo.iam.oauth;

import depromeet.ohgzoo.iam.jwt.SpyJwtService;
import depromeet.ohgzoo.iam.member.MemberJoinRequest;
import depromeet.ohgzoo.iam.member.MemberService;
import depromeet.ohgzoo.iam.oauth.kakao.KakaoProfileResponse;
import depromeet.ohgzoo.iam.oauth.kakao.KakaoProfileResponse.KakaoAccount;
import depromeet.ohgzoo.iam.oauth.kakao.KakaoProfileResponse.KakaoProfile;
import depromeet.ohgzoo.iam.oauth.kakao.KakaoTokenResponse;
import depromeet.ohgzoo.iam.oauth.kakao.SpyKakaoClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class OauthServiceImplTest {

    private OauthService oauthService;
    private SpyKakaoClient spyKakaoClient;
    private SpyJwtService spyJwtService;
    private MemberService spyMemberService;

    @BeforeEach
    void setUp() {
        spyMemberService = mock(MemberService.class);
        spyKakaoClient = new SpyKakaoClient();
        spyJwtService = new SpyJwtService();
        oauthService = new OauthServiceImpl(spyKakaoClient, spyJwtService, spyMemberService);

        given(spyMemberService.alreadyJoin(any())).willReturn(true);
    }

    @Test
    void getLoginUrl_returnsOAuth2LoginUrl() {
        Oauth2LoginUrl result = oauthService.getLoginUrl();

        String expected = new StringBuilder("https://kauth.kakao.com/oauth/authorize")
                .append("?client_id=null")
                .append("&response_type=code")
                .append("&redirect_uri=null")
                .toString();

        assertThat(result.getLoginUrl()).isEqualTo(expected);
    }

    @Test
    void getToken_passesCodeToClient() {
        oauthService.getToken("givenCode");

        assertThat(spyKakaoClient.getToken_argumentRequest.getCode()).isEqualTo("givenCode");
    }

    @Test
    void getToken_passesKakaoTokenResponseToClient() {
        String givenAccessToken = "accessToken";
        spyKakaoClient.getToken_returnValue = new KakaoTokenResponse(givenAccessToken, null, null, 0, null);

        oauthService.getToken(null);

        assertThat(spyKakaoClient.getProfile_argumentAuthorization).isEqualTo("Bearer " + givenAccessToken);
    }

    /* mockito example */

    @Test
    void getToken_passesProfileEmailToMemberService() {
        spyKakaoClient.getProfile_returnValue = new KakaoProfileResponse(null, new KakaoAccount("givenEmail", null));

        oauthService.getToken(null);

        ArgumentCaptor<String> stringArgumentCaptor = ArgumentCaptor.forClass(String.class);
        verify(spyMemberService, times(1)).alreadyJoin(stringArgumentCaptor.capture());
        assertThat(stringArgumentCaptor.getValue()).isEqualTo("givenEmail");
    }

    @Test
    void getToken_passesProfileToMemberService_whenMemberIsNotJoined() {
        given(spyMemberService.alreadyJoin(any())).willReturn(false);
        spyKakaoClient.getProfile_returnValue = new KakaoProfileResponse(null,
                new KakaoAccount("givenEmail", new KakaoProfile("givenNickName")));

        oauthService.getToken(null);

        ArgumentCaptor<MemberJoinRequest> joinRequestArgumentCaptor = ArgumentCaptor.forClass(MemberJoinRequest.class);
        verify(spyMemberService, times(1)).join(joinRequestArgumentCaptor.capture());

        assertThat(joinRequestArgumentCaptor.getValue().getEmail()).isEqualTo("givenEmail");
        assertThat(joinRequestArgumentCaptor.getValue().getProfileImg()).isEqualTo("");
        assertThat(joinRequestArgumentCaptor.getValue().getNickname()).isEqualTo("givenNickName");
    }

    @Test
    void getToken_inquiryMemberIdFromMemberService() {
        spyKakaoClient.getProfile_returnValue = new KakaoProfileResponse(null, new KakaoAccount("givenEmail", null));

        oauthService.getToken(null);

        ArgumentCaptor<String> stringArgumentCaptor = ArgumentCaptor.forClass(String.class);
        verify(spyMemberService).getMemberId(stringArgumentCaptor.capture());
        assertThat(stringArgumentCaptor.getValue()).isEqualTo("givenEmail");
    }

    @Test
    void getToken_passesMemberIdToJwtService_forAuthAndRefresh() {
        given(spyMemberService.getMemberId(any())).willReturn(1L);
        spyKakaoClient.getProfile_returnValue = new KakaoProfileResponse(null, new KakaoAccount("givenEmail", null));

        oauthService.getToken(null);

        assertThat(spyJwtService.issuedToken_callCount).isEqualTo(2);
        assertThat(spyJwtService.getIssuedToken_argumentSubject()).isEqualTo("1");
        assertThat(spyJwtService.getIssuedToken_argumentRole()).isEqualTo("USER");
        assertThat(spyJwtService.getIssuedToken_argumentPeriod()).isEqualTo(3600);

        assertThat(spyJwtService.getIssuedToken_argumentSubject()).isEqualTo("1");
        assertThat(spyJwtService.getIssuedToken_argumentRole()).isEqualTo("USER");
        assertThat(spyJwtService.getIssuedToken_argumentPeriod()).isEqualTo(36000);
    }
}