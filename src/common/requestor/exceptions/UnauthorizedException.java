package common.requestor.exceptions;

import common.credentials.Credentials;

public class UnauthorizedException extends MiddleAirException {
    public static final String CODE = "401";

    public UnauthorizedException(Credentials person) {
        this(person.toString());
    }

    public UnauthorizedException(String message) {
        super("Comando não autorizado: " + message);
    }

    public UnauthorizedException() {
        super("Comando não autorizado.");
    }
}
