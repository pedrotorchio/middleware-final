package common.clientproxy;

import common.remoteservice.RemoteService;
import common.requestor.Invocation;
import common.requestor.Requestor;

import java.io.Serializable;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;

/**
 * CLIENT PROXY RECEBE CHAMADAS A METODOS REMOTOS COM ASSINATURA ESPECÍFICA
 */

public class ClientProxy extends RemoteService implements Serializable {

    public ClientProxy(){}
    public ClientProxy(ClientProxy original){
        uid = original.getUid();
        host = original.getHost();
        port = original.getPort();
    }

    public String call (String name, String... parameters){

        Invocation invoc = prepareInvocation(name, parameters);

        return new Requestor()
                .invoke(invoc)
                .getResult();
    }


}
