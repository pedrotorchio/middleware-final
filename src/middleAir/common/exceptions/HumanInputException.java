package middleAir.common.exceptions;

import middleAir.common.types.Credentials;

public class HumanInputException extends MiddleAirException {

    public HumanInputException(Credentials person) {
        this(person.toString());
    }
    public HumanInputException(String message) {
        super(message);
    }
    public HumanInputException() {
        super();
    }

    public static String getCode(){
        return "301";
    }
}
