package common.requestor.exceptions;

public class MiddleAirException extends Exception {
    public static final String CODE = "404";

    public MiddleAirException(String message) {
        super(message);
    }
}
