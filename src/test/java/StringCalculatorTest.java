import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class StringCalculatorTest {

    private StringCalculator stringCalculator;

    @BeforeEach
    void setUp() {
        stringCalculator = new StringCalculator();
    }

    @Test
    void add_should_return_0_for_empty_entry() {
        final String emptyString = "";
        final int result = stringCalculator.add(emptyString);
        assertThat(result).isEqualTo(0);
    }
}
