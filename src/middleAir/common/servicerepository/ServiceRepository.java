package middleAir.common.servicerepository;

import middleAir.common.remoteservice.Service;

import java.util.Hashtable;

public class ServiceRepository<T extends Service> extends Hashtable<String, T> {

    public T lookup(String uid) {
        System.out.println("Lookup " + uid + " in");
        System.out.println("\t" + toString() + "\n");
        return get(uid);
    }

    public ServiceRepository<T> bind(String uid, T service) {
        put(uid, service);
        return this;
    }


}
