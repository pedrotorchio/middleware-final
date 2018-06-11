package controllers.drivers;

import controllers.simulators.ChanceSimulator;

public abstract class AirplaneComponentDriver {

    public double work(double value) {
        return work(value, getLaxity());
    }
    public double work(double value, int laxity) {
        ChanceSimulator chance = new ChanceSimulator();
        value = chance.range(value, laxity);

        return value;
    }
    public void sleep(long ms){
        try {
            Thread.sleep((int)work(ms, 5));

        } catch (InterruptedException e) {}
    }

    protected abstract int getLaxity();

}
