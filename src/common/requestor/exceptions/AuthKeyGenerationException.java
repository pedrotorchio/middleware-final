package common.requestor.exceptions;

public class AuthKeyGenerationException extends InternalErrorException {

    public AuthKeyGenerationException(String message) {
        super(message);
    }

    public AuthKeyGenerationException() {
        super("");
    }
}
