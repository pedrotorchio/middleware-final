package middleAir.common.remoteservice;

import middleAir.common.exceptions.MiddleAirException;
import middleAir.common.exceptions.TimeoutException;
import middleAir.common.invoker.Time;
import middleAir.common.requesthandler.Request;

public abstract class InstanceService extends Service {
    protected InterruptedCallback interruptedCB = new InterruptedCallback() {public void act() {} };
    protected boolean interrupted = false;
    public final Request execute(Request req, String methodname, String... parameters){
        setInterrupted(false);
        String result = "";

        try{

            result = call(req, methodname, parameters);

            if(req.getHeader().containsKey("timeout"))
                req.addHeader("timeout", "OK");

        } catch (MiddleAirException e) {
            req = e.interceptRequest(req);
            result = e.getMessage();
        }

        req.addHeader("done", Time.now().toString());
        req.setBody(result);
        return req;
    }

    public abstract String call(Request req, String methodname, String[] parameters) throws MiddleAirException;
    public abstract boolean isProtected();

    protected void setReturnMeaning(Request req, String meaning){
        req.addHeader("body", meaning);
    }

    public void interrupt(){
        setInterrupted(true);
        interruptedCB.act();
    }
    protected void setInterrupted(boolean stats){
        interrupted = stats;
    }
    protected boolean wasInterrupted(){ return interrupted; }
    protected void setInterruptedCallback(InterruptedCallback callback){
        interruptedCB = callback;
    }
    protected void throwTimeout(String value) throws TimeoutException {
        throw new TimeoutException(value);
    }
    protected interface InterruptedCallback{
        void act();
    }
}
