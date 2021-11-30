import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;


public class StringCalculatorTest {

    private static final String NEWLINE = System.lineSeparator();
    private StringCalculator stringCalculator;


    @BeforeEach
    void setUp() {
        stringCalculator = new StringCalculator(new Mapper(), new FormatValidator());
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


    private static Stream<Arguments> givenCommaSeparatedNumbers() {
        return Stream.of(
                Arguments.of("2,3", 5),
                Arguments.of("2,3,4", 9),
                Arguments.of("2,3,4,5,6,15", 35)
        );
    }

    @ParameterizedTest
    @MethodSource("givenCommaSeparatedNumbers")
    void add_should_return_the_sum_of_given_numbers_if_given_entry_contains_comma_separated_numbers(String givenCommaSeparatedNumbers, int expectedResult) {
        final int sum = stringCalculator.add(givenCommaSeparatedNumbers);
        assertThat(sum).as("for given entry " + givenCommaSeparatedNumbers).isEqualTo(expectedResult);
    }

    @Test
    void add_should_return_the_sum_of_given_numbers_if_entry_is_numbers_having_either_comma_or_newline_as_delimiter() {

        final String givenCommaAndNewlineSeparatedNumbers = "1" + NEWLINE + "2,3";

        final int sum = stringCalculator.add(givenCommaAndNewlineSeparatedNumbers);

        assertThat(sum).isEqualTo(6);
    }


    private static Stream<Arguments> givenCommaAndNewlineFollowingEachOther() {
        return Stream.of(
                Arguments.of("1" + NEWLINE + NEWLINE + "2,3"),
                Arguments.of("1," + NEWLINE + "2,3"),
                Arguments.of("1,,2,3"),
                Arguments.of("," + NEWLINE + "2,3"),
                Arguments.of("1,5," + NEWLINE),
                Arguments.of("1," + NEWLINE + NEWLINE + NEWLINE + ",3"),
                Arguments.of("1,,,,2,3")
        );
    }


    @ParameterizedTest
    @MethodSource("givenCommaAndNewlineFollowingEachOther")
    void add_should_throw_exception_if_implicit_delimiters_are_following_each_other(String givenCommaAndNewlineFollowingEachOther) {

        IllegalArgumentException illegalArgumentException = Assertions.assertThrows(
                IllegalArgumentException.class, () -> stringCalculator.add(givenCommaAndNewlineFollowingEachOther));
        Assertions.assertEquals("invalid entry : two delimiters must not follow each other", illegalArgumentException.getMessage());

    }


    private static Stream<Arguments> givenEntriesWithDelimiters() {
        return Stream.of(
              /*  Arguments.of("//," + NEWLINE + "1,2"),
                Arguments.of("//" + NEWLINE + NEWLINE + "1" + NEWLINE + "2"),*/
                Arguments.of("//-" + NEWLINE + "1-2")
        );
    }

    @ParameterizedTest
    @MethodSource("givenEntriesWithDelimiters")
    void add_should_accept_as_first_line_a_delimiter(String givenEntriesWithDelimiters) {

        final int sum = stringCalculator.add(givenEntriesWithDelimiters);
        assertThat(sum).isEqualTo(3);

    }

    private static Stream<Arguments> givenImplicitDelimiterNegativeNumbers() {
        return Stream.of(
                Arguments.of("1,-2"),
                Arguments.of("-1,2"),
                Arguments.of("-1" + NEWLINE + "2"),
                Arguments.of("1" + NEWLINE + "-2")
        );
    }

    private static Stream<Arguments> givenExplicitDelimiterNegativeNumbers() {
        return Stream.of(
                Arguments.of("//;" + NEWLINE + "1;-2"),
                Arguments.of("//;" + NEWLINE + "-1;2"),
                Arguments.of("//" + NEWLINE + NEWLINE + "-1" + NEWLINE + "2"),
                Arguments.of("//," + NEWLINE + NEWLINE + "1" + NEWLINE + "-2")
        );
    }

    private static Stream<Arguments> givenExplicitDashSeparator() {
        return Stream.of(
                Arguments.of("//-" + NEWLINE + "-1-2"),
                Arguments.of("//-" + NEWLINE + "1--2")
        );
    }

    @ParameterizedTest
    @MethodSource("givenImplicitDelimiterNegativeNumbers")
    void add_should_throw_exception_if_no_given_separator_and_negative_numbers_found(String givenImplicitDelimiterNegativeNumbers) {
        IllegalArgumentException illegalArgumentException = Assertions.assertThrows(
                IllegalArgumentException.class, () -> stringCalculator.add(givenImplicitDelimiterNegativeNumbers));
        assertThat(illegalArgumentException.getMessage()).startsWith("invalid entry : negative numbers not allowed");
    }

    @ParameterizedTest
    @MethodSource("givenExplicitDelimiterNegativeNumbers")
    void add_should_throw_exception_if_given_explicit_separator_and_negative_numbers_found(String givenExplicitDelimiterNegativeNumbers) {
        IllegalArgumentException illegalArgumentException = Assertions.assertThrows(
                IllegalArgumentException.class, () -> stringCalculator.add(givenExplicitDelimiterNegativeNumbers));
        assertThat(illegalArgumentException.getMessage()).startsWith("invalid entry : negative numbers not allowed");
    }

    @ParameterizedTest
    @MethodSource("givenExplicitDashSeparator")
    void add_should_throw_exception_if_explicit_dash_separator_and_negative_numbers_found(String givenExplicitDashSeparator) {
        IllegalArgumentException illegalArgumentException = Assertions.assertThrows(
                IllegalArgumentException.class, () -> stringCalculator.add(givenExplicitDashSeparator));
        assertThat(illegalArgumentException.getMessage()).startsWith("invalid entry : negative numbers not allowed");
    }


}
