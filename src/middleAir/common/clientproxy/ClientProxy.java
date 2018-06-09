package middleAir.common.clientproxy;

import controllers.cockpit.components.IOutputChannel;
import middleAir.common.exceptions.*;
import middleAir.common.remoteservice.RemoteService;
import middleAir.common.requesthandler.IHasHeader;
import middleAir.common.requestor.Invocation;
import middleAir.common.requestor.Requestor;

import java.io.IOException;
import java.lang.reflect.MalformedParametersException;
import java.util.Hashtable;

/**
 * CLIENT PROXY RECEBE CHAMADAS A METODOS REMOTOS COM ASSINATURA ESPECÍFICA
 */

public class ClientProxy extends RemoteService implements IHasHeader<ClientProxy>, IOutputChannel {

    protected final int RETRIES = 3;
    protected IOutputChannel output;
    protected Hashtable<String, String> header = new Hashtable();

    public ClientProxy(){}

    public ClientProxy(ClientProxy original){
        uid = original.getUid();
        host = original.getHost();
        port = original.getPort();
        header = original.getHeader();
        output = original.getOutput();
    }

    public ClientProxy setOutput(IOutputChannel out) {
        output = out;
        return this;
    }

    public IOutputChannel getOutput() {
        return output;
    }

    public ClientProxy addHeader(String key, String value) {
        header.put(key, value);
        return this;
    }

    public String getHeader(String key) {

        return header.get(key);
    }

    public Hashtable<String, String> getHeader() {
        return header;
    }

    public ClientProxy setHeader(Hashtable header) {
        this.header = header;
        return this;
    }


    public String call(String name, String... parameters)
            throws
                NotFoundException,
                UnauthorizedException,
                IOException,
                InternalErrorException,
                HumanInputException,
                TimeoutException {

        Invocation invoc = prepareInvocation(name, parameters);
        invoc.setHeader(getHeader());

        String result = new Requestor()
                .invoke(invoc)
                .getResult();

        return result;
    }

    public void write(String message){

        if(output != null)
            output.write(message);
    }

    public void writeError(String message){
        if(output != null)
            output.writeError(message);
        else
            System.out.println("\n\t$ " + message + "\n");
    }

    public static String serialize(ClientProxy cp){

        return String.format("%s:%s:%s", cp.getHost(), cp.getPort(), cp.getUid());
    }
    public static ClientProxy deserialize(String cp) throws  MalformedParametersException{
        String[] args = cp.split(":");
        ClientProxy service = null;

        if (args.length == 3) {
            service = new ClientProxy();
            service.setHost(args[0]);
            service.setPort(Integer.parseInt(args[1]));
            service.setUid(args[2]);

        }else
            throw new MalformedParametersException();

        return service;
    }
}
