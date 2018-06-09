package middleAir.common.exceptions;

public class NotFoundException extends MiddleAirException {

    public NotFoundException(String what) {
        super(what + " não encontrado");
    }
    public NotFoundException(){ super(); }

    public static String getCode(){
        return "404";
    }
}
