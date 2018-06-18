package middleAir.common.servicerepository;

import middleAir.common.logger.Logger;
import middleAir.common.remoteservice.Service;

import java.util.Hashtable;

public class ServiceRepository<T extends Service> extends Hashtable<String, T> {

    public T lookup(String uid) {
        Logger.getSingleton().maybePrintln("Lookup.. ",
                uid + " in" + "\n\t" + toString()).log();
        return get(uid);
    }

    public ServiceRepository<T> bind(String uid, T service) {
        put(uid, service);
        return this;
    }


}
