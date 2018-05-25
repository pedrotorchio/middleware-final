package stringmanipulationservices.service;

import common.remoteservice.InstanceService;
import common.requesthandler.Request;
import common.requestor.exceptions.NotFoundException;

import java.util.function.Predicate;

public class StringExtractorService extends InstanceService implements IStringExtractor {

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

        return filtered;
    }


    public Request execute(Request req, String name, String... parameters) {
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
                req.addHeader("error", NotFoundException.CODE);
                result = "Método não encontrado.";
        }

        req.setBody(result);
        return req;
    }

    @Override
    public boolean isProtected() {
        return false;
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
