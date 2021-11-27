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
        final int sum = stringCalculator.add(emptyString);
        assertThat(sum).isEqualTo(0);
    }

    @Test
    void add_should_return_given_entry_if_contains_only_one_number() {
        final String givenOneNumber = "2";
        final int sum = stringCalculator.add(givenOneNumber);
        assertThat(sum).isEqualTo(2);
    }

    @Test
    void add_should_return_the_sum_of_given_numbers_if_given_entry_contains_two_comma_separated_numbers() {
        final String givenTwoSeparatedNumbers = "2,3";
        final int sum = stringCalculator.add(givenTwoSeparatedNumbers);
        assertThat(sum).isEqualTo(5);
    }
}
