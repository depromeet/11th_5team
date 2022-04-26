package depromeet.ohgzoo.iam.jwt;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class JwtServiceImplTest {

    private JwtService jwtService;

    @BeforeEach
    void setUp() {
        jwtService = new JwtServiceImpl();
    }

    @Test
    void 토큰_만료_테스트() {
        String expired = jwtService.issuedToken("subject", "USER", -100000);
        String valid = jwtService.issuedToken("subject", "USER", 1000000);

        assertThat(jwtService.verifyToken(expired)).isFalse();
        assertThat(jwtService.verifyToken(valid)).isTrue();
    }

    @Test
    void 토큰_subject_테스트() {
        String token = jwtService.issuedToken("subject", "USER", 1000000);

        assertThat(jwtService.getSubject(token)).isEqualTo("subject");
    }
}