package controllers.cockpit.proxies;

import controllers.cockpit.components.IYoke;
import middleAir.common.clientproxy.ClientProxy;
import middleAir.common.exceptions.InternalErrorException;
import middleAir.common.exceptions.TimeoutException;
import middleAir.common.invoker.Time;

public class YokeProxy extends ClientProxy implements IYoke {

    public YokeProxy(ClientProxy original){

        super(original);
    }
    @Override
    public int steer(int deg) {
        addHeader("timeout", Time.secondsLater(60).toString());
        try {
            return Integer.parseInt(call("steer", "" + deg));

        } catch (InternalErrorException e) {
            writeError("Erro de steering.");
        } catch (TimeoutException e) {
            writeError("Timeout.");
        } catch (Exception e) {}

        return 0;
    }

    @Override
    public int rise(int deg) {
        addHeader("timeout", Time.secondsLater(60).toString());
        try {
            return Integer.parseInt(call("rise", "" + deg));

        } catch (InternalErrorException e) {
            writeError("Erro de diving.");
        } catch (TimeoutException e) {
            writeError("Timeout.");
        } catch (Exception e) {}

        return 0;
    }

    public static void main(String[] args){
        new YokeProxy(new ClientProxy()).rise(4);
    }
}
