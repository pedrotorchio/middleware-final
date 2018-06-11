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
        addHeader("timeout", Time.secondsLater(power/100).toString());
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
        addHeader("timeout", Time.secondsLater(power/100).toString());
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
        addHeader("timeout", Time.secondsLater(5).toString());
        String result ;
        try {
            result = call("on");

        } catch(TimeoutException e){
            return e.getMessage().equals("true");
        } catch (Exception e) {
            writeError(e.getMessage());
            return false;
        }

        return result.equals("true");
    }

    public boolean off() {
        addHeader("timeout", Time.secondsLater(5).toString());
        String result;
        try {
            result = call("off");

        } catch(TimeoutException e){
            return e.getMessage().equals("true");
        } catch (Exception e) {
            writeError(e.getMessage());
            return false;
        }
        return result.equals("true");
    }
}
