package names;

import client.clientproxy.ClientProxy;
import client.clientproxy.ISubString;
import client.clientproxy.SubStringClientProxy;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class NamingProxy {
    public ClientProxy lookup(String name){
        InetAddress ip = null;
        try {
            ip = InetAddress.getLocalHost();

        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

        ClientProxy dummy = new SubStringClientProxy();
                    dummy.setHost(ip);
                    dummy.setPort(8080);
                    dummy.setUid("1");


        return dummy;
    }
}
