package exception;

public class ValidationException extends RuntimeException {
    public ValidationException(String exception) {
        super(exception);
    }
}
