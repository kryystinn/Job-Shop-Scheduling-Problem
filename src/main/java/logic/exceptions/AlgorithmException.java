package logic.exceptions;

public class AlgorithmException extends Exception {
    public AlgorithmException(String message) {
        super(message);
    }

    public AlgorithmException(Exception e) {
        super(e);
    }
}
