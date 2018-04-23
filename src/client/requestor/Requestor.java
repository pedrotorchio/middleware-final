package client.requestor;

import client.requesthandler.Request;
import client.requesthandler.RequestHandler;

import java.io.IOException;

public class Requestor<T> {
    static final String protocolFormat = "%s/%s@%s"; // ([arg1, arg2, arg3]/methodName@objUid)

    public Invocation invoke(Invocation invoc)
                                                throws IOException {
        System.out.println(invoc.toString());
        System.out.println();

        Request req = mkRequest(invoc);
        Request resp = new RequestHandler()
                .send(req)
                .receive();

        return invoc.setResult(resp.getBody());
    }

    public Request mkRequest(Invocation invoc){
        Request req = new Request();
        req.addHeader("host", invoc.getIp().getHostAddress());
        req.addHeader("port", ""+invoc.getPort());
        req.setBody(mkBody(
                invoc.getParameters().toString(),
                invoc.getMethodName(),
                invoc.getUid())
        );

        return req;
    }
    protected String mkBody(String parameters, String methodName, String objId){
        return String.format(protocolFormat,
            parameters,
            methodName,
            objId
        );
    }
}

