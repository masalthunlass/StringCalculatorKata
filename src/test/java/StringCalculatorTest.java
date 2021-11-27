import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

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


    private static Stream<Arguments> givenEntries() {
        return Stream.of(
                Arguments.of("2,3", 5),
                Arguments.of("2,3,4", 9),
                Arguments.of("2,3,4,5,6,15", 35)
        );
    }

    @ParameterizedTest
    @MethodSource("givenEntries")
    void add_should_return_the_sum_of_given_numbers_if_given_entry_contains_comma_separated_numbers(String givenCommaSeparatedNumbers, int expectedResult) {
        final int sum = stringCalculator.add(givenCommaSeparatedNumbers);
        assertThat(sum).as("for given entry " + givenCommaSeparatedNumbers).isEqualTo(expectedResult);
    }

}
