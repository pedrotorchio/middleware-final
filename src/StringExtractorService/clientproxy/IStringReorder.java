package StringExtractorService.clientproxy;


import java.rmi.UnknownHostException;

public interface IStringReorder {
    String lexicographical(String original) throws UnknownHostException;
    String vowelsFirst(String original) throws UnknownHostException;
    String consonantsFirst(String original) throws UnknownHostException;
}
