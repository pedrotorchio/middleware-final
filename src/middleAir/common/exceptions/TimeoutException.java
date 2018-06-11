package middleAir.common.exceptions;

import middleAir.common.requesthandler.Request;

public class TimeoutException extends MiddleAirException {

    public TimeoutException(String value) {
         super(value);
    }
    public TimeoutException(){super("Tempo esgotado.");}

    public static String getCode(){
        return "501";
    }
    protected String _getCode(){
        return getCode();
    }
    protected String _getBodyMeaning(){
        return "Resultado obtido";
    }
    public Request interceptRequest(Request req){
        req = super.interceptRequest(req);
        req.addHeader("timeout", "FAILED");

        return req;
    }
}

