package est.TDD_practice.exception;

public class MinimumOrderAmountException extends RuntimeException {
    public MinimumOrderAmountException(String message) {
        super(message);
    }
}
