package controllers.cockpit.proxies;

import controllers.cockpit.components.IOutputChannel;
import middleAir.common.clientproxy.ClientProxy;
import middleAir.common.invoker.Time;

public class MonitorProxy extends ClientProxy implements IOutputChannel {

    public MonitorProxy(ClientProxy cp){
        super(cp);
        addHeader("timeout", Time.secondsLater(2).toString());
    }
    public void write(String message){
        addHeader("timeout", Time.secondsLater(1).toString());
        try {
            call("write", message);

        } catch (Exception e) {}
    }

    public void writeError(String message){
        addHeader("timeout", Time.secondsLater(1).toString());

        try {
            call("writeError", message);

        } catch (Exception e) {}

    }
}
