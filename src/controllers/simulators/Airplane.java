package controllers.simulators;

import controllers.drivers.Engine;
import controllers.drivers.Flap;

public class Airplane {
    // conjunto de drivers de componentes instalados

    public Engine ell = new Engine();
    public Engine elr = new Engine();

    public Engine erl = new Engine();
    public Engine err = new Engine();

    public Flap fl = new Flap();
    public Flap fr = new Flap();

    public Flap ft = new Flap();

    public int getPropulsion(){
        int power = 0;
        for(Engine e : getEngineArray())
            power += e.getPower();

        // cada grau de inclinação dos flaps reduz 5% a potencia total

        int rotation = 0;
        for(Flap f : getFlapArray())
            rotation += Math.abs(f.getAngle());

        power -= rotation * power * 5/100;

        return power;
    }

    public int getSpeed(){
        // simplificadamente, velocidade será um múltiplo do poder de propulsão, desconsiderando aceleração
        // cada unidade de propulsão equivale a 2 km/h
        return getPropulsion() * 2;
    }
    private Engine[] getEngineArray(){
        Engine[] array = {ell, elr, erl, err};
        return array;
    }
    private Flap[] getFlapArray(){
        Flap[] array = {fl, fr};
        return array;
    }

    public String flapSummary(){
        return fl.getAngle() + " " + fr.getAngle();
    }
    public String engineSummary(){
        return ell.getPower() + " " + elr.getPower() + " | " + erl.getPower() + " " + err.getPower();
    }
}
