package common.requestor;

import common.requesthandler.Request;
import common.requesthandler.RequestHandler;
import common.requestor.exceptions.InvalidMethodException;
import common.requestor.exceptions.NotFoundException;
import common.requestor.exceptions.UnauthorizedException;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Requestor<T> {
    static final String protocolFormat = "%s/%s@%s"; // ([arg1, arg2, arg3]/methodName@objUid)
    static final String captureGroups  = "(.*)/(.*)@(.*)";


    public Invocation invoke(Invocation invoc) throws IOException, InvalidMethodException, UnauthorizedException, NotFoundException {

        System.out.println("Invoking " + invoc.getMethodName() + "@" + invoc.getUid() + "\n");

        Request req = mkRequest(invoc);
        Socket sock = mkSocket(invoc);

        req = new RequestHandler(sock, req)
                .send()
                .receive()
                .getRequest();

        if (req.getHeader().containsKey("error")) {
            switch (req.getHeader("error")) {
                case InvalidMethodException.CODE:
                    throw new InvalidMethodException(invoc);
                case UnauthorizedException.CODE:
                    throw new UnauthorizedException();
                case NotFoundException.CODE:
                    throw new NotFoundException("Método");
            }
        }
        invoc = new Invocation()
                .setResult(req.getBody())
                .setHeader(req.getHeader());


        return invoc.setResult(req.getBody());
    }


    public Socket mkSocket(Invocation invoc) throws IOException {
        return new Socket(
                invoc.getIp(),
                invoc.getPort());
    }
    public Request mkRequest(Invocation invoc){

        Request req = new Request();
        req.setHeader(invoc.getHeader());
        req.setBody(mkBody(
                Arrays.toString(invoc.getParameters()).replaceAll(", ", ","),
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
                             .setParameters(parameters.toArray(new String[1]));

        System.out.println(invocation.toString() + "\n");

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

