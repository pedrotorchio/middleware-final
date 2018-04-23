package StringExtractorService.clientproxy;

import java.io.IOException;
import java.rmi.UnknownHostException;

public interface ISubString {
    String getVowels(String original) throws UnknownHostException, IOException;
    String getConsonants(String original) throws IOException;
}
