import java.util.stream.Stream;

public class StringCalculator {

    private final Mapper mapper;
    private final FormatValidator formatValidator;

    public StringCalculator(Mapper mapper, FormatValidator formatValidator) {
        this.mapper = mapper;
        this.formatValidator = formatValidator;
    }

    int add(final String separatedNumbers) {
        if (separatedNumbers.isBlank()) return 0;
        final Stream<Operand> operands = this.formatValidator.andThen(this.mapper).apply(separatedNumbers);
        return operands
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
