package controllers.drivers;

import controllers.simulators.ChanceSimulator;

public abstract class AirplaneComponentDriver {

    public double work(double value) {
        ChanceSimulator chance = new ChanceSimulator();
        value = chance.range(value, getLaxity());

        return value;
    }

    protected abstract int getLaxity();

}
