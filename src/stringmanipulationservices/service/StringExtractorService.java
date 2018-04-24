package stringmanipulationservices.service;

import common.remoteservice.RemoteService;

import java.util.function.Predicate;

public class StringExtractorService extends RemoteService implements IStringExtractor {

    public StringExtractorService(){
        uid = "str-extractor";
    }

    @Override
    public String getVowels(String original) {
        System.out.println("getVowels " + original);

        return filter(original, character -> isVowel(character));
    }

    @Override
    public String getConsonants(String original) {
        System.out.println("getConsonants " + original);

        return filter(original, character -> !isVowel(character) && !character.equals(" "));
    }

    public String filter(String original, Predicate<String> check){
        String filtered = "";
        String c;
        for(int i = 0; i < original.length() ; i++)
            if(check.test((c = ""+original.charAt(i))))
                filtered += c;

        System.out.println("filter");
        System.out.println(original);
        System.out.println(filtered);
        return filtered;
    }



    public String call(String name, String... parameters){
        String result = "";
        String arg1;
        switch(name){
            case "getVowels":
                arg1 = parameters[0];
                result = getVowels(arg1);
                break;
            case "getConsonants":
                arg1 = parameters[0];
                result = getConsonants(arg1);
                break;

            default:
                result = "!METHOD NOT FOUND";
        }

        return result;
    }
    protected boolean isVowel(String c){
        return isVowel(c.charAt(0));
    }
    protected boolean isVowel(char c){
        return c == 'a' || c == 'A' ||
               c == 'e' || c == 'E' ||
               c == 'i' || c == 'I' ||
               c == 'o' || c == 'O' ||
               c == 'u' || c == 'U';
    }
}
