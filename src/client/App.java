package client;

import client.clientproxy.StringExtractorClientProxy;
import names.NamingProxy;

import java.io.IOException;

public class App {
    public static void main(String[] args){
        NamingProxy np = new NamingProxy();

            ((StringExtractorClientProxy) np.lookup("IMPLEMENTAR NAMING SERVICE"))
                    .getVowels("Get My Vowels");


    }
}
