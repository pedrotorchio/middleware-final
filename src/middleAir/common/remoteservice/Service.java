package middleAir.common.remoteservice;

import middleAir.common.requestor.Invocation;

public abstract class Service {
    protected String host = "localhost";
    protected int port = 80;
    protected String uid = null;


    public String getHost() {
        return host;
    }

    public int getPort() {
        return port;
    }

    public String getUid() {
        return uid;
    }

    public Service setHost(String host) {
        this.host = host;
        return this;
    }

    public Service setPort(int port) {
        this.port = port;
        return this;
    }

    public Service setUid(String uid) {
        this.uid = uid;
        return this;
    }

    public String toString() {
        return serialize(this);
    }
    public static String serialize(Service service){
        return String.format("%s:%s:%s", service.getHost(), service.getPort(), service.getUid());
    }


    public Invocation prepareInvocation(String methodName, String[] parameters) {
        Invocation invoc = new Invocation();
        invoc.setMethodName(methodName);
        invoc.setParameters(parameters);
        invoc.setIp(getHost());
        invoc.setPort(getPort());
        invoc.setObjectId(getUid());

        return invoc;
    }
}
