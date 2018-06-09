package middleAir.common.remoteservice;

import middleAir.common.exceptions.MiddleAirException;
import middleAir.common.requesthandler.Request;

public abstract class InstanceService extends Service {
    protected String intermediateValue = null;
    public final Request execute(Request req, String methodname, String... parameters){
        setIntermediateValue(null);
        String result = "";

        try{

            result = call(req, methodname, parameters);

        } catch (MiddleAirException e) {
            req = e.interceptRequest(req);
            result = e.getMessage();
        }

        req.setBody(result);
        return req;
    }
    public abstract String call(Request req, String methodname, String[] parameters) throws MiddleAirException;
    public abstract boolean isProtected();
    public void setIntermediateValue(String value){
        intermediateValue = value;
    }
    public String getIntermediateValue(){
        return intermediateValue;
    }
}
