package controllers.cockpit.proxies;

import controllers.cockpit.components.IThrottle;
import middleAir.common.clientproxy.ClientProxy;
import middleAir.common.exceptions.TimeoutException;
import middleAir.common.invoker.Time;


public class ThrottleProxy extends ClientProxy implements IThrottle {
    public ThrottleProxy(ClientProxy cp){
        super(cp);
    }

    public int powerUp(int power) {
        addHeader("timeout", Time.secondsLater(10).toString());
        try {
            power = Integer.parseInt(call("powerUp", ""+power));
        } catch( TimeoutException e){
            power = Integer.parseInt(e.getMessage());
        } catch (Exception e) {
            return 0;
        }

        return power;
    }

    public int powerDown(int power) {
        addHeader("timeout", Time.secondsLater(10).toString());
        try {
            power = Integer.parseInt(call("powerDown", ""+power));

        }catch( TimeoutException e){
            power = Integer.parseInt(e.getMessage());
        } catch (Exception e) {
            return 0;
        }

        return power;
    }

    public boolean on() {
        addHeader("timeout", Time.secondsLater(2).toString());
        try {
            call("on");

        } catch (Exception e) {
            writeError(e.getMessage());
            return false;
        }
        return true;
    }

    public boolean off() {
        addHeader("timeout", Time.secondsLater(2).toString());
        try {
            call("off");

        } catch (Exception e) {
            writeError(e.getMessage());
            return false;
        }
        return true;
    }
}
