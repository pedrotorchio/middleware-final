package middleAir.common.exceptions;

public class NotFoundException extends MiddleAirException {

    public NotFoundException(String what) {
        super(what);
    }
    public NotFoundException(){ super(); }

    public static String getCode(){
        return "404";
    }
    protected String _getCode(){
        return getCode();
    }
}
