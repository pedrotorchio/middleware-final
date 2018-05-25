package common.requestor.exceptions;

public class InternalErrorException extends MiddleAirException {
    public static final String CODE = "500";

    public InternalErrorException(String message) {
        super(message);
    }
}
