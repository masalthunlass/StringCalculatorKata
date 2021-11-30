import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FormatValidator implements Function<String, String> {


    @Override
    public String apply(final String separatedNumbers) {
        validateIfNoImplicitSeparatorFollowingEachOther(separatedNumbers);
        validateIfNotNegativeNumber(separatedNumbers);
        return separatedNumbers;
    }

    private void validateIfNoImplicitSeparatorFollowingEachOther(String separatedNumbers) {
        final String IMPLICIT_SEPARATORS_FOLLOWING_EACH_OTHER = "([^/]*)((,\\R)+|,{2,}|\\R{2,})(.*)";
        if (separatedNumbers.matches(IMPLICIT_SEPARATORS_FOLLOWING_EACH_OTHER)) {
            throw new IllegalArgumentException("invalid entry : two delimiters must not follow each other");
        }
    }

    private void validateIfNotNegativeNumber(String separatedNumbers) {
        final String NEGATIVE_NUMBER_FOUND = "(-[0-9])";
        final String DASH_SEPARATOR = "^//-\\R";
        Pattern negativeNumberPattern = Pattern.compile(NEGATIVE_NUMBER_FOUND);
        Pattern dashSeparatorPattern = Pattern.compile(DASH_SEPARATOR);
        if (dashSeparatorPattern.matcher(separatedNumbers).find()) {
            final String DASH_SEPARATOR_NEGATIVE_NUMBER_FOUND = '(' + DASH_SEPARATOR + ".+(-{2}[0-9]))|(" + DASH_SEPARATOR + "(-[0-9]))";
            negativeNumberPattern = Pattern.compile(DASH_SEPARATOR_NEGATIVE_NUMBER_FOUND);
        }

        final Matcher matcher = negativeNumberPattern.matcher(separatedNumbers);
        if (matcher.find()) {
            throw new IllegalArgumentException("invalid entry : negative numbers not allowed");
        }
    }
}
