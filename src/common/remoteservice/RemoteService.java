package common.remoteservice;

import common.requestor.Invocation;

import java.io.Serializable;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;

/**
 * CLIENT PROXY RECEBE CHAMADAS A METODOS REMOTOS COM ASSINATURA ESPECÍFICA
 */

public abstract class RemoteService implements Serializable, IRemoteService {
    protected InetAddress host;
    protected int port;
    protected String uid;

    public RemoteService(String id) throws UnknownHostException {
        host = InetAddress.getLocalHost();
        uid  = id;
        port = 8742;
    }

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
    public abstract String call(String name, ArrayList<String> parameters);

}
