package common.requestor;

import common.requesthandler.Request;

import java.util.Arrays;
import java.util.Hashtable;

public class Invocation extends Request {
    protected String uid;
    protected int port;
    protected String ip;
    protected String methodName;
    protected String[] parameters;

    public Invocation() {
    }

    public Invocation(Invocation other) {
        super(other);

        uid = other.getUid();
        port = other.getPort();
        ip = other.getIp();
        methodName = other.getMethodName();
        parameters = other.getParameters();
    }
    public Invocation setResult(String result) {
        setBody(result);
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
    public Invocation setIp(String ip){
        this.ip = ip;
        return this;
    }

    public Invocation setMethodName(String methodName){
        this.methodName = methodName;
        return this;
    }
    public Invocation setParameters(String[] parameters){
        this.parameters = parameters;
        return this;
    }

    public Invocation setHeader(Hashtable<String, String> header) {
        super.setHeader(header);
        return this;
    }
    public String getMethodName() {
        return methodName;
    }

    public String[] getParameters() {
        return parameters;
    }

    public String getIp() {
        return ip;
    }

    public int getPort() {
        return port;
    }

    public String getUid() {
        return uid;
    }

    public String getResult() {
        return getBody();
    }

    public String toString(){
        String about = "Invocation: \n";

        if(getIp() != null)
            about += "Host: " + getIp() + ":" + getPort() + "\n";


        about += "Method: " + methodName + " @ " + getUid() + "\n";
        about += getParameters().length + " Parameters: " + Arrays.toString(getParameters());

        return about;
    }
}
