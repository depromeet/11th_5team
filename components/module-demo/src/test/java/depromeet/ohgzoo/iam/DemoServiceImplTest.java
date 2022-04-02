package depromeet.ohgzoo.iam;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class DemoServiceImplTest {

    private DemoService demoService;

    @BeforeEach
    void setUp() {
        demoService = new DemoServiceImpl();
    }

    @Test
    void getDemo_returnsDemo() {
        Demo expected = new Demo("hello world");

        Demo actual = demoService.getDemo();

        assertThat(actual).usingRecursiveComparison()
                .isEqualTo(expected);
    }
}