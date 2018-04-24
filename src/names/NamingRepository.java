package names;

import common.clientproxy.ClientProxy;
import common.remoteservice.RemoteService;
import common.servicerepository.ServiceRepository;

import java.net.UnknownHostException;
import java.rmi.Remote;
import java.util.Iterator;

public class NamingRepository extends RemoteService implements INaming {

    ServiceRepository repository = new ServiceRepository();

    public NamingRepository(){
        uid = "naming-service";
    }

    public <T extends RemoteService> T lookup(String uid) {
        return (T)repository.lookup(uid);
    }

    public INaming bind(String uid, RemoteService service) {
        repository.bind(uid, service);
        return this;
    }

    public String[] list() throws UnknownHostException {
        String[] names = new String[repository.size()];

        Iterator<String> keys = repository.keys().asIterator();
        for(int i = 0 ; keys.hasNext() ; i++)
            names[i] = keys.next();
        return names;
    }

    public String call(String name, String... parameters) {
        String result = "";
        String arg0, arg1, arg2, arg3;
        switch(name){
            case "lookup":
                RemoteService service = lookup(parameters[0]);
                if(service == null)
                    result = "null";
                else
                    result = service.toString();

                break;
            case "bind":
                arg0 = parameters[0];
                arg1 = parameters[1];
                arg2 = parameters[2];

                RemoteService proxy = new ClientProxy()
                                .setHost(arg0)
                                .setPort(Integer.parseInt(arg1))
                                .setUid(arg2);
                bind(arg2, proxy);

                result = proxy.toString();

        }

        return result;
    }
}
