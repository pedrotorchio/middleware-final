package controllers.cockpit.components;

import middleAir.common.exceptions.TimeoutException;

public interface IThrottle {
    int powerUp(int power) throws TimeoutException;
    int powerDown(int power) throws TimeoutException;
    boolean on() throws TimeoutException;
    boolean off() throws TimeoutException;
    int getPower();
    boolean isOn();
}
