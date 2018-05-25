package common.namingproxy;

import common.clientproxy.ClientProxy;
import common.remoteservice.Service;
import common.requestor.exceptions.NotFoundException;
import names.INaming;

public class NamingProxy extends ClientProxy implements INaming {


    public NamingProxy(String host) {
        this.host = host;
        this.port = 9000;
        this.uid = "naming-service";
    }

    public ClientProxy lookup(String name) throws Exception {

        String[] args = call("lookup", name).split(":");

        ClientProxy service = null;

        if (args.length == 3) {
            service = new ClientProxy();
            service.setHost(args[0]);
            service.setPort(Integer.parseInt(args[1]));
            service.setUid(args[2]);

        } else
            throw new NotFoundException("Serviço encontrado é inválido.");

        return service;
    }

    public INaming bind(String uid, Service service) {
        try {
            call("bind",
                    service.getHost(), "" + service.getPort(), service.getUid());

        } catch (Exception e) {
            e.printStackTrace();
        }

        return this;
    }

    @Override
    public String[] list() {
        return new String[0];
    }
}
