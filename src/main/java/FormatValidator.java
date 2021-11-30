import java.util.function.Function;

public class FormatValidator implements Function<String, String> {


    private static final String IMPLICIT_SEPARATORS_FOLLOWING_EACH_OTHER = "([^/]*)((,\\R)+|,{2,}|\\R{2,})(.*)";

    @Override
    public String apply(final String separatedNumbers) {
        validateIfNoImplicitSeparatorFollowingEachOther(separatedNumbers);

        return separatedNumbers;
    }

    private void validateIfNoImplicitSeparatorFollowingEachOther(String separatedNumbers) {
        if (separatedNumbers.matches(IMPLICIT_SEPARATORS_FOLLOWING_EACH_OTHER)) {
            throw new IllegalArgumentException("invalid entry : two delimiters must not follow each other");
        }
    }


}
