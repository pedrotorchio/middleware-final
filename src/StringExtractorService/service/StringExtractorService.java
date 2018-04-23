package StringExtractorService.service;

import common.remoteservice.RemoteService;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

public class StringExtractorService extends RemoteService implements IStringExtractor {

    public StringExtractorService() throws UnknownHostException {
        super("str-extractor");
    }

    @Override
    public String getVowels(String original) {
        System.out.println("getVowels " + original);
        String vowels = "";
        char c;
        for(int i = 0; i < original.length() ; i++)
            if(isVowel(c = original.charAt(i)))
                vowels += c;

        return vowels;
    }

    @Override
    public String getConsonants(String original) {
        System.out.println("getConsonants " + original);
        String consonants = "";
        char c;
        for(int i = 0; i < original.length() ; i++)
            if(!isVowel(c = original.charAt(i)))
                consonants += c;

        return consonants;
    }


    @Override
    public String call(String name, ArrayList<String> parameters){
        String result = "";
        String arg1;
        switch(name){
            case "getVowels":
                arg1 = parameters.get(0);
                result = getVowels(arg1);
                break;
            case "getConsonantes":
                arg1 = parameters.get(0);
                result = getConsonants(arg1);
                break;
        }

        return result;
    }

    protected boolean isVowel(char c){
        return c == 'a' || c == 'A' ||
               c == 'e' || c == 'E' ||
               c == 'i' || c == 'I' ||
               c == 'o' || c == 'O' ||
               c == 'u' || c == 'U';
    }
}
