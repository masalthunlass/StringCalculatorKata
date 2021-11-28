public class StringCalculator {

    private final Mapper mapper;

    public StringCalculator(Mapper mapper) {
        this.mapper = mapper;
    }

    int add(final String separatedNumbers) {
        if (separatedNumbers.isBlank()) return 0;

        return this.mapper.apply(separatedNumbers)
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
