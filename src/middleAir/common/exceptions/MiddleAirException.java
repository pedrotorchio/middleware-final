package middleAir.common.exceptions;

import middleAir.common.requesthandler.Request;

public abstract class MiddleAirException extends Exception {

    public MiddleAirException(String message) {
        super(message);
    }
    public MiddleAirException(){ super(); }

    public Request interceptRequest(Request req){
        req.addHeader("error", _getCode());
        req.addHeader("body", _getBodyMeaning());
        req.setBody(getMessage());

        return req;
    }
    protected String _getBodyMeaning(){
        return "Mensagem de erro";
    }
    abstract protected String _getCode();
}
