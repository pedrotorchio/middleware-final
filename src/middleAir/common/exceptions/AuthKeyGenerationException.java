package middleAir.common.exceptions;

public class AuthKeyGenerationException extends InternalErrorException {

    public AuthKeyGenerationException(String message) {
        super(message);
    }
    public AuthKeyGenerationException() {
        super();
    }

    public static String getCode(){
        return "501";
    }

}
