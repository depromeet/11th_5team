package depromeet.ohgzoo.iam.posts;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class ListToStringConverterTest {

    @Test
    void 빈문자는_빈배열을_반환한다() {
        ListToStringConverter sut = new ListToStringConverter();

        List<String> result = sut.convertToEntityAttribute("");

        assertThat(result).isEmpty();
    }
}