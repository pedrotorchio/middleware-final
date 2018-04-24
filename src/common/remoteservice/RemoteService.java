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

public abstract class RemoteService implements Serializable {

    protected String host = "localhost";
    protected int port    = 80;
    protected String uid  = "lacks-name";

    public String getHost(){
        return host;
    }
    public int getPort(){
        return port;
    }
    public String getUid(){
        return uid;
    }

    public RemoteService setHost(String host){
        this.host = host;
        return this;
    }
    public RemoteService setPort(int port){
        this.port = port;
        return this;
    }
    public RemoteService setUid(String uid){
        this.uid = uid;
        return this;
    }

    public String toString(){
        return String.format("%s:%s:%s", host, port, uid);
    }

    public Invocation prepareInvocation(String methodName, String[] parameters){
        Invocation invoc = new Invocation();
        invoc.setMethodName(methodName);
        invoc.setParameters(parameters);
        invoc.setIp(getHost());
        invoc.setPort(getPort());
        invoc.setObjectId(getUid());

        return invoc;
    }

    public abstract String call(String name, String... parameters);

}
