package names;

import common.clientproxy.ClientProxy;
import common.remoteservice.RemoteService;

import java.net.UnknownHostException;
import java.util.Arrays;

public class NamingProxy extends ClientProxy implements INaming{


    public NamingProxy(String host){
        this.host = host;
        this.port = 9000;
        this.uid  = "naming-service";
    }
    public <T extends RemoteService> T lookup(String name){

        String[] args = call("lookup", name).split(":");

        T service = (T) new ClientProxy()
                                    .setHost(args[0])
                                    .setPort(Integer.parseInt(args[1]))
                                    .setUid(args[2]);


        return service;
    }


    public INaming bind(String uid, RemoteService service) {
        call("bind",
            service.getHost(),""+service.getPort(),service.getUid());

        return this;
    }

    @Override
    public String[] list() throws UnknownHostException {
        return new String[0];
    }
}
