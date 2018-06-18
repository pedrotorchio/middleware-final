package middleAir.naming;

import middleAir.common.clientproxy.ClientProxy;
import middleAir.common.exceptions.MiddleAirException;
import middleAir.common.exceptions.NotFoundException;
import middleAir.common.remoteservice.InstanceService;
import middleAir.common.remoteservice.RemoteService;
import middleAir.common.remoteservice.Service;
import middleAir.common.requesthandler.Request;
import middleAir.common.servicerepository.ServiceRepository;

class NamingService extends InstanceService implements INaming {

    ServiceRepository<Service> repository = new ServiceRepository();

    public NamingService() {
        uid = "naming-service";
    }

    public RemoteService lookup(String uid) throws NotFoundException {
        RemoteService service = (RemoteService)repository.lookup(uid);
        if(service == null)
            throw new NotFoundException("Serviço " + uid);

        return service;
    }

    public INaming bind(Service service) {

        repository.bind(service.getUid(), service);
        return this;
    }

    public String call(Request req, String methodname, String[] parameters) throws MiddleAirException{


        String arg0, arg1, arg2;

        switch (methodname) {

            case "lookup":
                RemoteService service = lookup(parameters[0]);
                    return  RemoteService.serialize(service);

            case "bind":
                arg0 = parameters[0];
                arg1 = parameters[1];
                arg2 = parameters[2];

                ClientProxy proxy = new ClientProxy();

                proxy.setHost(arg0);
                proxy.setPort(Integer.parseInt(arg1));
                proxy.setUid(arg2);

                bind(proxy);

                    return ClientProxy.serialize(proxy);


            default:
                return "DEFAULT";
        }

    }

    public boolean isProtected() {
        return false;
    }
}
