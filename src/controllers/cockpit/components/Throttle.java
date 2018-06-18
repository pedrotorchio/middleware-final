package controllers.cockpit.components;

import controllers.drivers.Engine;
import controllers.simulators.Airplane;
import middleAir.common.exceptions.MiddleAirException;
import middleAir.common.exceptions.TimeoutException;
import middleAir.common.remoteservice.InstanceService;
import middleAir.common.requesthandler.Request;

public class Throttle extends InstanceService implements IThrottle {
    Airplane plane;

    boolean enginesOn = false;

    public Throttle(Airplane plane){
        this.plane = plane;
        uid = "air-throttle";
    }
    public int powerUp(int power) throws TimeoutException {
        return power(power);
    }

    public int powerDown(int power) throws TimeoutException {
        return power(-power);
    }

    protected int power(int power) throws TimeoutException {
        power = power/4;
        int reached = 0;

        if(wasInterrupted())
            throwTimeout(""+reached);
        plane.ell.power(power);
        reached += plane.ell.getPower();

        if(wasInterrupted())
            throwTimeout(""+reached);
        plane.elr.power(power);
        reached += plane.elr.getPower();

        if(wasInterrupted())
            throwTimeout(""+reached);
        plane.erl.power(power);
        reached += plane.erl.getPower();

        if(wasInterrupted())
            throwTimeout(""+reached);
        plane.err.power(power);
        reached += plane.err.getPower();

        return reached;

    }
    private Engine[] getEngineArray(){
        Engine[] engs = {
                plane.ell,
                plane.elr,
                plane.erl,
                plane.err
        };

        return engs;
    }

    public boolean on() throws TimeoutException { // transação
        return switchEngines(true);
    }

    public boolean off() throws TimeoutException { // transação
        return switchEngines(false);
    }

    public int getPower() {
        return plane.ell.getPower() + plane.elr.getPower() + plane.erl.getPower() + plane.err.getPower();
    }

    public boolean isOn() {
        return enginesOn;
    }

    public boolean switchEngines(boolean on) throws TimeoutException { //true on, false off

        // liga todas as turbinas
        // se for interrompido, reverte resultados parciais

        for(Engine engine : getEngineArray()) {
            if (on)
                engine.on();
            else
                engine.off();

            if(wasInterrupted())
            {
                if (!on)
                    engine.on();
                else
                    engine.off();

                throwTimeout("false");
            }
        }

        enginesOn = on;

        return true;
    }

    public String call(Request req, String methodname, String[] parameters) throws MiddleAirException {
        String result;
        switch(methodname){
            case "powerUp":

                result = ""+powerUp(Integer.parseInt(parameters[0]));
                setReturnMeaning(req, "Potência resultante");
                return result;
            case "powerDown":
                result = ""+powerDown(Integer.parseInt(parameters[0]));
                setReturnMeaning(req, "Potência resultante");
                return result;
            case "on":
                result = ""+on();
                setReturnMeaning(req, "SE foi possivel ligar TODOS motores");
                return result;

            case "off":
                result = ""+off();
                setReturnMeaning(req, "SE foi possivel desligar TODOS motores");
                return result;
            case "isOn":
                result = isOn() ? "true" : "false";
                setReturnMeaning(req, "SE motores estão ligados");
                return result;
            case "getPower":
                result = ""+getPower();
                setReturnMeaning(req,"Soma das potências dos motores");
                return result;

            default:
                return "DEFAULT";
        }
    }

    public boolean isProtected() {
        return true;
    }
}
