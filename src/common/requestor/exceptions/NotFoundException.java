package common.requestor.exceptions;

public class NotFoundException extends MiddleAirException {
    public static final String CODE = "404";

    public NotFoundException(String what) {
        super(what + " não encontrado");
    }
}
