package logic.exceptions;

public class InputException extends Exception {

    public InputException(String message) {
        super(message);
    }

    public InputException(Exception e) {
        super(e);
    }
}
