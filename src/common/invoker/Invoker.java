package common.invoker;

import common.servicerepository.ServiceRepository;
import common.remoteservice.RemoteService;
import common.requesthandler.Request;
import common.requesthandler.RequestHandler;
import common.requestor.Invocation;
import common.requestor.Requestor;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Invoker {
    ServiceRepository repository = new ServiceRepository();

    public Invoker registerService(RemoteService service){
        repository.register(service.getUid(), service);
        return this;
    }
    public void listen(int port) throws IOException {
        RequestHandler rh;

        ServerSocket server = new ServerSocket(port);

        while(true){
            Socket sock = server.accept();

            System.out.println("New Connection");
            System.out.println(sock.toString() + "\n");

            rh = new RequestHandler(
                    sock,
                    new Request());
            Request req = rh.receive()
                            .getRequest();

            Invocation      invoc = new Requestor().mkInvocation(req);

            RemoteService service = repository.lookup(invoc.getUid());

            String         result = service.call(invoc.getMethodName(), invoc.getParameters());

            rh.setRequest(req.setBody(result))
              .send();

            rh.close();

        }

    }
}
