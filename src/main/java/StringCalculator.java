import java.util.Arrays;

public class StringCalculator {


    int add(String numbers) {
        if (numbers.isBlank()) return 0;
        return Arrays.stream(numbers.split(","))
                .map(Integer::parseInt)
                .reduce(0, Integer::sum);
    }
}
