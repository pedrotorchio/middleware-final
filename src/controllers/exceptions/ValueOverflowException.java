package controllers.exceptions;

public class ValueOverflowException extends ComponentException{

    public ValueOverflowException(String message, double value) {
        super(message, value);

    }

}
