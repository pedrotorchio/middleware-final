package middleAir.common.exceptions;

import middleAir.common.requesthandler.Request;

public class TimeoutException extends MiddleAirException {

    public TimeoutException(String tempo) {
        super("Tempo esgotado. " + tempo);
    }
    public TimeoutException(){super();}

    public static String getCode(){
        return "501";
    }
    public Request interceptRequest(Request req){
        req.addHeader("error", TimeoutException.getCode());
        req.addHeader("timeout", "FAILED");

        return req;
    }
}

