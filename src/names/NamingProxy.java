package names;

import client.clientproxy.StringExtractorClientProxy;
import common.clientproxy.ClientProxy;

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

        ClientProxy dummy = new StringExtractorClientProxy();
                    dummy.setHost(ip);
                    dummy.setPort(8609);
                    dummy.setUid("str-extractor");


        return dummy;
    }
}
