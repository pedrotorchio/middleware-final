package common.requestor.exceptions;

import common.requestor.Invocation;

public class InvalidMethodException extends MiddleAirException {
    public static final String CODE = "400";

    public InvalidMethodException(Invocation method) {
        this(method.toString());
    }

    public InvalidMethodException(String message) {
        super("Invocação inválida: " + message);
    }
}
