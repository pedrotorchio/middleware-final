package controllers.exceptions;


public class ComponentException extends Exception{

    public static String CODE = "600";

    double value;

    public ComponentException(String message, double value) {
        super(message);
        this.value = value;
    }

    public double getValue(){
        return value;
    }
    public void   setValue(double value){this.value = value;}


}
