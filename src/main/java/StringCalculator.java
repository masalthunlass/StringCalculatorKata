import java.util.Arrays;

public class StringCalculator {


    private static final String SEPARATOR = ",";

    int add(String commaSeparatedNumbers) {
        if (commaSeparatedNumbers.isBlank()) return 0;
        final String[] numbers = commaSeparatedNumbers.split(SEPARATOR);
        return Arrays.stream(numbers)
                .map(Operand::new)
                .map(Operand::getValue)
                .reduce(0, Integer::sum);
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
