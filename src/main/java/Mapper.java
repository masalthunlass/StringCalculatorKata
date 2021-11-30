import java.util.Arrays;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class Mapper implements Function<String, Stream<StringCalculator.Operand>> {

    private static final String COMMA_OR_NEW_LINE_SEPARATOR = ",|\\R";

    public Stream<StringCalculator.Operand> apply(final String separatedNumbers) {
        final String numbersSeparator = findNumbersSeparator(separatedNumbers);
        final String cleanSeparatedNumbers = removeExplicitSeparator(separatedNumbers);
        return Arrays.stream(cleanSeparatedNumbers.split(numbersSeparator)).map(StringCalculator.Operand::new);
    }

    private String removeExplicitSeparator(String separatedNumbers) {
        return separatedNumbers.replaceFirst("//(.|\\R)\\R", "");
    }

    private String findNumbersSeparator(final String separatedNumbers) {
        final Pattern explicitSeparatorMatcherPattern = Pattern.compile("^//(.)\\R");
        final Matcher matcher = explicitSeparatorMatcherPattern.matcher(separatedNumbers);
        if (matcher.find()) {
            return matcher.group(1);
        }
        return COMMA_OR_NEW_LINE_SEPARATOR;
    }

}
