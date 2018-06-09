package middleAir.common.exceptions;

import middleAir.common.requestor.Invocation;

public class InvalidMethodException extends MiddleAirException {

    public InvalidMethodException(Invocation method) {
        this(method.toString());
    }

    public InvalidMethodException(String message) {
        super("Invocação inválida: " + message);
    }

    public static String getCode(){
        return "400";
    }
}
