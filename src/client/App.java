package client;

import client.clientproxy.ClientProxy;
import client.clientproxy.SubStringClientProxy;
import names.NamingProxy;

import java.io.IOException;
import java.rmi.UnknownHostException;

public class App {
    public static void main(String[] args){
        NamingProxy np = new NamingProxy();

        try {
            ((SubStringClientProxy) np.lookup("IMPLEMENTAR NAMING SERVICE"))
                    .getVowels("Get My Vowels");






        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
