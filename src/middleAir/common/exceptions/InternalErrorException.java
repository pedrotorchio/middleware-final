package middleAir.common.exceptions;

public class InternalErrorException extends MiddleAirException {

    public InternalErrorException(String message) {
        super(message);
    }
    public InternalErrorException(){ super(); }

    public static String getCode(){
        return "500";
    }
}
