package common.servicerepository;

import common.remoteservice.RemoteService;

import java.util.Hashtable;

public class ServiceRepository extends Hashtable<String, RemoteService> {

    public RemoteService lookup(String uid){
        System.out.println("Lookup " + uid);
        System.out.println(toString() + "\n");
        return get(uid);
    }
    public void register(String uid, RemoteService service){
        put(uid, service);
    }

}
