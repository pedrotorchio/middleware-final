package middleAir.common.requestor;

import middleAir.common.exceptions.*;
import middleAir.common.requesthandler.Request;
import middleAir.common.requesthandler.RequestHandler;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Requestor<T> {
    static final String protocolFormat = "%s/%s@%s"; // ([arg1, arg2, arg3]/methodName@objUid)
    static final String captureGroups  = "(.*)/(.*)@(.*)";


    public Invocation invoke(Invocation invoc)
            throws IOException,
                UnauthorizedException,
                NotFoundException,
                TimeoutException,
                HumanInputException,
                InternalErrorException {

        System.out.println("Invoking " + invoc.getMethodName() + "@" + invoc.getUid() + "\n");

        Request req = mkRequest(invoc);
        Socket sock = mkSocket(invoc);

        req = new RequestHandler(sock, req)
                .send()
                .receive()
                .getRequest();

        if (req.getHeader().containsKey("error"))
            throwError(req);

        invoc = new Invocation()
                .setResult(req.getBody())
                .setHeader(req.getHeader());


        return invoc.setResult(req.getBody());
    }
    public void throwError(Request req)
            throws  TimeoutException,
                    InternalErrorException,
                    HumanInputException,
                    UnauthorizedException,
                    NotFoundException {

            String code = req.getBody();
            String msg  = req.getBody();

            if(code.equals(TimeoutException.getCode()))
                throw new TimeoutException(msg);
            if(code.equals(InternalErrorException.getCode()))
                throw new InternalErrorException(msg);
            if(code.equals(HumanInputException.getCode()))
                throw new HumanInputException(msg);
            if(code.equals(UnauthorizedException.getCode()))
                throw new UnauthorizedException(msg);
            if(code.equals(NotFoundException.getCode()))
                throw new NotFoundException(msg);

    }

    public Socket mkSocket(Invocation invoc) throws IOException {
        try {
            return new Socket(
                    invoc.getIp(),
                    invoc.getPort());
        }catch(Exception e){
            throw new IOException(e.getMessage());
        }
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

