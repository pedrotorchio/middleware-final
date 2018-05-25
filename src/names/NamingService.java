package names;

import common.clientproxy.ClientProxy;
import common.remoteservice.InstanceService;
import common.remoteservice.RemoteService;
import common.remoteservice.Service;
import common.requesthandler.Request;
import common.requestor.exceptions.NotFoundException;
import common.servicerepository.ServiceRepository;

import java.util.Iterator;

public class NamingService extends InstanceService implements INaming {

    ServiceRepository<Service> repository = new ServiceRepository();

    public NamingService() {
        uid = "naming-service";
    }

    public <T extends RemoteService> T lookup(String uid) {
        return (T)repository.lookup(uid);
    }

    public INaming bind(String uid, Service service) {

        repository.bind(uid, service);
        return this;
    }

    public String[] list() {
        String[] names = new String[repository.size()];

        Iterator<String> keys = repository.keys().asIterator();
        for(int i = 0 ; keys.hasNext() ; i++)
            names[i] = keys.next();
        return names;
    }


    public Request execute(Request req, String methodname, String... parameters) {
        String result = "";
        String arg0, arg1, arg2, arg3;

        switch (methodname) {
            case "lookup":
                RemoteService service = lookup(parameters[0]);

                if (service == null) {
                    req.addHeader("error", NotFoundException.CODE);
                    result = "Serviço não encontrado.";
                } else
                    result = service.toString();

                break;
            case "bind":
                arg0 = parameters[0];
                arg1 = parameters[1];
                arg2 = parameters[2];

                ClientProxy proxy = new ClientProxy();
                proxy.setHost(arg0);
                proxy.setPort(Integer.parseInt(arg1));
                proxy.setUid(arg2);

                bind(arg2, proxy);

                result = proxy.toString();
        }
        req.setBody(result);
        return req;
    }

    public boolean isProtected() {
        return false;
    }
}
