package depromeet.ohgzoo.iam.sucurity;

import depromeet.ohgzoo.iam.jwt.JwtService;
import depromeet.ohgzoo.iam.security.controller.TokenRefreshController;
import depromeet.ohgzoo.iam.security.dto.AuthToken;
import depromeet.ohgzoo.iam.security.exception.UnAuthenticationException;
import depromeet.ohgzoo.iam.security.service.TokenRefreshService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation;
import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TokenRefreshController.class)
@ExtendWith(RestDocumentationExtension.class)
@Import(RestDocsConfig.class)
public class TokenRefreshControllerTest {

    @MockBean
    TokenRefreshService tokenRefreshService;

    @MockBean
    JwtService jwtService;

    MockMvc mockMvc;

    @Autowired
    RestDocumentationResultHandler restDocs;

    @BeforeEach
    void setUp(WebApplicationContext context, RestDocumentationContextProvider provider) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context)
                .apply(MockMvcRestDocumentation.documentationConfiguration(provider))
                .alwaysDo(MockMvcResultHandlers.print())
                .alwaysDo(restDocs)
                .addFilter(new CharacterEncodingFilter("UTF-8", true))
                .build();
    }

    @Test
    @DisplayName("refresh_성공")
    public void refresh_성공() throws Exception {
        AuthToken authToken = new AuthToken("authToken", "refreshToken");
        when(tokenRefreshService.createNewAuthToken(any())).thenReturn(authToken);

        mockMvc.perform(get("/refresh"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.auth").value("authToken"))
                .andExpect(jsonPath("$.refresh").value("refreshToken"))
                .andDo(
                        restDocs.document(
                                responseFields(
                                        fieldWithPath("auth").description("authToken"),
                                        fieldWithPath("refresh").description("refreshToken")
                                )
                        )
                );
    }

    @Test
    @DisplayName("refresh_실패")
    public void refresh_실패() throws Exception {
        UnAuthenticationException unAuthenticationException = new UnAuthenticationException("토큰이 만료되었습니다.");
        when(tokenRefreshService.createNewAuthToken(any())).thenThrow(unAuthenticationException);

        mockMvc.perform(get("/refresh"))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.msg").value("토큰이 만료되었습니다."))
                .andExpect(jsonPath("$.code").value("401"))
                .andDo(
                        restDocs.document(
                                responseFields(
                                        fieldWithPath("msg").description("토큰이 만료되었습니다."),
                                        fieldWithPath("code").description("401")
                                )
                        )
                );
    }
}