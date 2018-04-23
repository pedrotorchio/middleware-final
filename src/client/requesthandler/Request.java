package client.requesthandler;

import java.util.Hashtable;

public class Request {
    protected Hashtable<String, String> header = new Hashtable();
    protected String body;

    public void addHeader(String key, String value){
        header.put(key, value);
    }
    public void setBody(String content){
        body = content;
    }
    public void setBody(Object anycontent){
        body = anycontent.toString();
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