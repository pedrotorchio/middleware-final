package middleAir.common.exceptions;

import middleAir.common.requesthandler.Request;

public class MiddleAirException extends Exception {

    public MiddleAirException(String message) {
        super(message);
    }
    public MiddleAirException(){ super(); }

    public Request interceptRequest(Request req){
        req.addHeader("error", getCode());
        req.setBody(getMessage());

        return req;
    }

    public static String getCode(){
        return "000";
    }
}
