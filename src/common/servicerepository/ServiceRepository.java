package common.servicerepository;

import common.clientproxy.ClientProxy;
import common.remoteservice.RemoteService;

import java.rmi.Remote;
import java.util.Hashtable;

public class ServiceRepository extends Hashtable<String, RemoteService> {

    public RemoteService lookup(String uid){
        System.out.println("Lookup " + uid + " in");
        System.out.println("\t" + toString() + "\n");
        return get(uid);
    }
    public ServiceRepository bind(String uid, RemoteService service){
        put(uid, service);
        return this;
    }


}
