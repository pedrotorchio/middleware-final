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
        int reached = 0;

        setIntermediateValue(""+0);
        plane.ell.power(power);
        reached += plane.ell.getPower();
        setIntermediateValue(""+reached);

        plane.elr.power(power);
        reached += plane.elr.getPower();
        setIntermediateValue(""+reached);

        plane.erl.power(power);
        reached += plane.erl.getPower();
        setIntermediateValue(""+reached);

        plane.err.power(power);
        reached += plane.err.getPower();
        setIntermediateValue(""+reached);

        return reached;

    }

    public boolean on() {
        setIntermediateValue("false");
        plane.ell.on();
        plane.elr.on();
        plane.erl.on();
        plane.err.on();
        setIntermediateValue("true");
        return true;
    }

    public boolean off() {
        setIntermediateValue("false");
        plane.ell.off();
        plane.elr.off();
        plane.erl.off();
        plane.err.off();
        setIntermediateValue("true");
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
