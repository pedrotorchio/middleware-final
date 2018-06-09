package middleAir.common.exceptions;

import middleAir.common.requesthandler.Request;
import middleAir.common.types.Credentials;

public class UnauthorizedException extends MiddleAirException {

    public UnauthorizedException(Credentials person) {
        this(person.toString());
    }
    public UnauthorizedException(String message) {
        super("Comando não autorizado: " + message);
    }
    public UnauthorizedException() {
        super("Comando não autorizado.");
    }

    public static String getCode(){
        return "401";
    }
    public Request interceptRequest(Request req){
        req = super.interceptRequest(req);
        req.addHeader("authorization", "DENIED");

        return req;
    }
}
