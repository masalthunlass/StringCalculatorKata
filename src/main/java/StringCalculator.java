import java.util.Arrays;
import java.util.stream.Stream;

public class StringCalculator {


    int add(final String separatedNumbers) {
        if (separatedNumbers.isBlank()) return 0;
        final Stream<Operand> numbers = map(separatedNumbers);
        return numbers.map(Operand::getValue).reduce(0, Integer::sum);
    }

    private Stream<Operand> map(String separatedNumbers) {
        final String DELIMITERS_FOLLOWING_EACH_OTHER = "(.*)((,\\R)+|,{2,}|\\R{2,})(.*)";
        if (separatedNumbers.matches(DELIMITERS_FOLLOWING_EACH_OTHER)) {
            throw new IllegalArgumentException("invalid entry : two delimiters must not follow each other");
        }
        final String COMMA_OR_NEW_LINE_SEPARATOR = ",|\\R";
        return Arrays.stream(separatedNumbers.split(COMMA_OR_NEW_LINE_SEPARATOR)).map(Operand::new);
    }


    public static class Operand {
        private final int value;

        public Operand(String value) {
            this.value = Integer.parseInt(value);
        }

        public int getValue() {
            return value;
        }
    }
}
