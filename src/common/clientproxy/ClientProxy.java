package common.clientproxy;

import common.requestor.Invocation;
import common.remoteservice.IRemoteService;

import java.io.Serializable;
import java.net.InetAddress;
import java.util.ArrayList;

/**
 * CLIENT PROXY RECEBE CHAMADAS A METODOS REMOTOS COM ASSINATURA ESPECÍFICA
 */

public abstract class ClientProxy implements Serializable, IRemoteService {
    protected InetAddress host;
    protected int port;
    protected String uid;

    public InetAddress getHost(){
        return host;
    }
    public int getPort(){
        return port;
    }
    public String getUid(){
        return uid;
    }

    public void setHost(InetAddress host){
        this.host = host;
    }
    public void setPort(int port){
        this.port = port;
    }
    public void setUid(String uid){
        this.uid = uid;
    }

    public Invocation prepareInvocation(String methodName, ArrayList<String> parameters){
        Invocation invoc = new Invocation();
        invoc.setMethodName(methodName);
        invoc.setParameters(parameters);
        invoc.setIp(getHost());
        invoc.setPort(getPort());
        invoc.setObjectId(getUid());

        return invoc;
    }
}
