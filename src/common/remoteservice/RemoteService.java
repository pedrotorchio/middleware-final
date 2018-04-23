package common.remoteservice;

import common.clientproxy.ClientProxy;
import common.requestor.Invocation;

import java.io.Serializable;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;

/**
 * CLIENT PROXY RECEBE CHAMADAS A METODOS REMOTOS COM ASSINATURA ESPECÍFICA
 */

public abstract class RemoteService extends ClientProxy implements Serializable {


    public RemoteService(String id) throws UnknownHostException {
        uid  = id;
        host = InetAddress.getLocalHost();
        port = 8742;
    }
    public abstract String call(String name, String... parameters);


}
