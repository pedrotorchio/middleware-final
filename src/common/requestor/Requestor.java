package common.requestor;

import common.requesthandler.Request;
import common.requesthandler.RequestHandler;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Requestor<T> {
    static final String protocolFormat = "%s/%s@%s"; // ([arg1, arg2, arg3]/methodName@objUid)
    static final String captureGroups  = "(.*)/(.*)@(.*)";

    public Invocation invoke(Invocation invoc){

        System.out.println(invoc.toString());
        System.out.println();

        Request req  = mkRequest(invoc);
        Socket sock  = null;
        Request resp = null;
        try {
            sock = mkSocket(invoc);


            resp = new RequestHandler(sock, req)
                    .send()
                    .receive()
                    .getRequest();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return invoc.setResult(resp.getBody());
    }

    public Socket mkSocket(Invocation invoc) throws IOException {
        return new Socket(
                invoc.getIp().getHostAddress(),
                invoc.getPort());
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
    public Invocation mkInvocation(Request request){

        Pattern p = Pattern.compile(captureGroups);
        Matcher m = p.matcher(request.getBody());
        m.find();

        String methodName = m.group(2);
        String        uid = m.group(3);

        ArrayList<String> parameters = new ArrayList();
            for(String parameter : m.group(1)
                                        .replaceAll("[\\[\\]]", "")
                                        .split(","))
                parameters.add(parameter);

        Invocation invocation = new Invocation()
                             .setMethodName(methodName)
                             .setObjectId(uid)
                             .setParameters(parameters);

        System.out.println(invocation.toString());

        return invocation;
    }
    protected String mkBody(String parameters, String methodName, String objId){
        return String.format(protocolFormat,
            parameters,
            methodName,
            objId
        );
    }

}

