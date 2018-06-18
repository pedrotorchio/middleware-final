package middleAir.common.invoker;

import middleAir.common.exceptions.TimeoutException;
import middleAir.common.interceptor.IInterceptor;
import middleAir.common.requesthandler.Request;

public class TimeoutInterceptor implements IInterceptor<Request, TimeoutException> {

    public Request intercept(Request req) throws TimeoutException {
        if(!req.getHeader().containsKey("timeout"))
            return req;

        Time toTime = getTime(req);

        if(hasPassed(toTime))
            throw new TimeoutException();


        return req;
    }
    public Time getTime(Request req){
        String timeout = req.getHeader("timeout");
        Time toTime = Time.parse(timeout);

        return toTime;
    }
    public boolean hasPassed(Time toTime){
        try {
            Time now = Time.now();

            return now.passed(toTime);
        }catch(Exception e){
            return false;
        }
    }
}

