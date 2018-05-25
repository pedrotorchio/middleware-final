package common.clientproxy;

import client.cockpit.Monitor;
import common.remoteservice.RemoteService;
import common.requesthandler.IHasHeader;
import common.requestor.Invocation;
import common.requestor.Requestor;
import common.requestor.exceptions.InvalidMethodException;
import common.requestor.exceptions.NotFoundException;
import common.requestor.exceptions.UnauthorizedException;

import java.io.IOException;
import java.util.Hashtable;

/**
 * CLIENT PROXY RECEBE CHAMADAS A METODOS REMOTOS COM ASSINATURA ESPECÍFICA
 */

public class ClientProxy<T> extends RemoteService implements IHasHeader<ClientProxy> {

    protected final int RETRIES = 3;
    protected Output output = new Monitor();
    protected Hashtable<String, String> header = new Hashtable();

    public ClientProxy(){}

    public ClientProxy(ClientProxy original){
        uid = original.getUid();
        host = original.getHost();
        port = original.getPort();
        header = original.getHeader();
        output = original.getOutput();
    }

    public ClientProxy setOutput(Output out) {
        output = out;
        return this;
    }

    public Output getOutput() {
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
            InvalidMethodException,
            NotFoundException,
            UnauthorizedException,
            IOException {

        Invocation invoc = prepareInvocation(name, parameters);
        invoc.setHeader(getHeader());

        String result = "";
        for (int i = 0; i < RETRIES; i++) {
            try {
                result = new Requestor()
                        .invoke(invoc)
                        .getResult();
                break;
            } catch (IOException e) {
                output.writeError("Erro de comunicação");
                throw e;
            }
        }

        return result;
    }

    public interface Output {
        void write(String message);

        void writeError(String message);
    }



}
