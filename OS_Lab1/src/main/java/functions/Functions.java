package functions;

import java.util.Optional;

public class Functions {

    public static Optional<Double> functionF(Integer x) {
        return x != 0 ? Optional.of(1.0 / x) : Optional.empty();
    }

    public static Optional<Double> functionG(Integer x) {
        return Optional.of((double) (x * x));
    }
}
