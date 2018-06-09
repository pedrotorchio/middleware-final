package controllers.cockpit.components;

public interface IThrottle {
    int powerUp(int power);
    int powerDown(int power);
    boolean on();
    boolean off();
}
