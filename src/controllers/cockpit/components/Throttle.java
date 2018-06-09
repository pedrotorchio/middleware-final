package controllers.cockpit.components;

import controllers.simulators.Airplane;
import middleAir.common.exceptions.MiddleAirException;
import middleAir.common.remoteservice.InstanceService;
import middleAir.common.requesthandler.Request;

public class Throttle extends InstanceService implements IThrottle {
    Airplane plane;

    public Throttle(Airplane plane){
        this.plane = plane;
        uid = "air-throttle";
    }
    public int powerUp(int power) {
        return power(power);
    }

    public int powerDown(int power) {
        return power(-power);
    }

    protected int power(int power){
        power = power/4;

        plane.ell.power(power);
        plane.elr.power(power);
        plane.erl.power(power);
        plane.err.power(power);

        return plane.ell.getPower() + plane.elr.getPower() + plane.erl.getPower() + plane.err.getPower();
    }

    public boolean on() {
        plane.ell.on();
        plane.elr.on();
        plane.erl.on();
        plane.err.on();

        return true;
    }

    public boolean off() {

        plane.ell.off();
        plane.elr.off();
        plane.erl.off();
        plane.err.off();

        return true;

    }

    public String call(Request req, String methodname, String[] parameters) throws MiddleAirException {
        switch(methodname){
            case "powerUp":
                return ""+powerUp(Integer.parseInt(parameters[0]));
            case "powerDown":
                return ""+powerDown(Integer.parseInt(parameters[0]));
            case "on":
                return ""+on();
            case "off":
                return ""+off();
            default:
                return "DEFAULT";
        }
    }

    public boolean isProtected() {
        return true;
    }
}
