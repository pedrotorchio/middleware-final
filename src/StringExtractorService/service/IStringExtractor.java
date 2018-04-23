package StringExtractorService.service;

import java.io.IOException;
import java.rmi.UnknownHostException;

public interface IStringExtractor {
    String getVowels(String original);
    String getConsonants(String original);
}
