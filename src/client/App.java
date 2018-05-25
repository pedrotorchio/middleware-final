package client;

import client.clientproxy.StringExtractorClientProxy;
import client.clientproxy.UpperLowerClientProxy;
import client.cockpit.KeyboardAuthentication;
import common.MiddleAir;
import common.credentials.IAuthenticationMethod;

public class App {

    public static void main(String[] args) throws Exception {

        IAuthenticationMethod key = new KeyboardAuthentication();

        MiddleAir ma = new MiddleAir("localhost");
        ma.authenticate(key.readAuthentication());

        if (!ma.checkComponents())
            return;


        String string = "ABCabcDEFdefGHI";
        UpperLowerClientProxy ul = new UpperLowerClientProxy(ma.lookup("upper-lower"));
        string = ul.toLowercase(string);
        StringExtractorClientProxy se = new StringExtractorClientProxy(ma.lookup("str-extractor"));
        string = se.getConsonants(string);

        System.out.println(string);
    }
}
