package common.requesthandler;

import common.requestor.exceptions.InvalidMethodException;
import common.requestor.exceptions.NotFoundException;
import common.requestor.exceptions.UnauthorizedException;

import java.util.Hashtable;

public class Request implements IHasHeader<Request> {
    protected Hashtable<String, String> header = new Hashtable();
    protected String body;

    public Request() {
    }

    public Request(Request other) {
        body = other.getBody();
        header = other.getHeader();
    }

    public Request addHeader(String key, String value){
        header.put(key, value);
        return this;
    }

    public Request setHeader(Hashtable<String, String> header) {
        this.header = header;
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

    public String getHeader(String key) {

        return header.get(key);
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

    public static final String STAT_INVMETH = InvalidMethodException.CODE;
    public static final String STAT_NOTAUTH = UnauthorizedException.CODE;
    public static final String STAT_NOTFOUND = NotFoundException.CODE;
}
