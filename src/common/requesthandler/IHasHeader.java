package common.requesthandler;

import java.util.Hashtable;

public interface IHasHeader<T> {
    T addHeader(String key, String value);

    String getHeader(String key);

    Hashtable<String, String> getHeader();

    T setHeader(Hashtable<String, String> header);
}
