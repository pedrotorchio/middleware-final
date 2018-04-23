package client.requestor;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Arrays;

public class Invocation {
    protected String uid;
    protected int port;
    protected InetAddress ip;
    protected String methodName;
    protected ArrayList<Object> parameters;
    protected String result;

    public Invocation setResult(String result) {
        this.result = result;
        return this;
    }
    public Invocation setObjectId(String uid){
        this.uid = uid;
        return this;
    }
    public Invocation setPort(int port){
        this.port = port;
        return this;
    }
    public Invocation setIp(InetAddress ip){
        this.ip = ip;
        return this;
    }

    public Invocation setMethodName(String methodName){
        this.methodName = methodName;
        return this;
    }
    public Invocation setParameters(ArrayList<Object> parameters){
        this.parameters = parameters;
        return this;
    }

    public String getMethodName() {
        return methodName;
    }

    public ArrayList<Object> getParameters() {
        return parameters;
    }

    public InetAddress getIp() {
        return ip;
    }

    public int getPort() {
        return port;
    }

    public String getUid() {
        return uid;
    }

    public String getResult() {
        return result;
    }

    public String toString(){
        return ("Invocation: ") + "\n" +
               ("Host: " + getIp().getHostAddress() + ":" + getPort()) + "\n" +
               ("Method: " + methodName + " @ " + getUid()) + "\n" +
               (getParameters().size() + " Parameters: " + Arrays.toString(getParameters().toArray()));
    }
}
