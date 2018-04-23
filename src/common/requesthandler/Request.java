package common.requesthandler;

import java.util.Hashtable;

public class Request {
    protected Hashtable<String, String> header = new Hashtable();
    protected String body;

    public Request addHeader(String key, String value){
        header.put(key, value);
        return this;
    }
    public Request setBody(String content){
        body = content;
        return this;
    }
    public Request setBody(Object anycontent){
        body = anycontent.toString();
        return this;
    }

    public Hashtable<String, String> getHeader() {
        return header;
    }

    public String getBody() {
        return body;
    }

    public String toString(){
        return "Request:" + "\n" +
               "Header: " + getHeader().toString() + "\n" +
               "Body: " + getBody();
    }
}
