package client;

import StringExtractorService.service.IStringExtractor;
import StringExtractorService.service.IUpperLower;
import StringExtractorService.service.UpperLowerService;
import client.clientproxy.StringExtractorClientProxy;
import client.clientproxy.UpperLowerClientProxy;
import names.NamingProxy;

public class App {

    public static void main(String[] args){
        NamingProxy np = new NamingProxy("localhost");

            IStringExtractor extractor = new StringExtractorClientProxy(np.lookup("str-extractor"));
            IUpperLower upperlower     = new UpperLowerClientProxy(np.lookup("upper-lower"));

            String text       = "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.";
            String consonants = extractor.getConsonants(text);
            String lowCons    = upperlower.toLowercase(consonants);
            String vowels     = extractor.getVowels(text);
            String upVowels   = upperlower.toUppercase(vowels);

            System.out.println("ORIGNIAL: " + text);
            System.out.println("VOWELS: " + vowels);
            System.out.println("CONSONANTS: " + consonants);
            System.out.println("LOW CONSONANTS: " + lowCons);
            System.out.println("UPPER VOWELS: " + upVowels);
    }
}
