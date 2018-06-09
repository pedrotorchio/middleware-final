package middleAir.naming;

import middleAir.common.clientproxy.ClientProxy;
import middleAir.common.exceptions.InvalidMethodException;
import middleAir.common.exceptions.NotFoundException;
import middleAir.common.remoteservice.Service;

public class NamingProxy extends ClientProxy implements INaming {


    public NamingProxy(String host) {
        this.host = host;
        this.port = 9000;
        this.uid = "naming-service";
    }

    public ClientProxy lookup(String name) throws NotFoundException {
        String result;
        try {
            result = call("lookup", name);
        }catch (Exception e){
            throw new NotFoundException(e.getMessage());
        }

        ClientProxy service = ClientProxy.deserialize(result);

        return service;
    }

    public NamingProxy bind(Service service) throws InvalidMethodException {
        if(service.getUid() == null)
            throw new InvalidMethodException("");
        try {
            call("bind",
                    service.getHost(), "" + service.getPort(), service.getUid());

        } catch (Exception e) {
            e.printStackTrace();
        }

        return this;
    }

}
