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
    final String INVALID_INVOK = "!";
    Callback callback = new Callback(){
        protected void run(String result, RemoteService service, Invocation invoc){}
    };

    public abstract static class Callback{
        public void execute(String result, RemoteService service, Invocation invoc){
            System.out.println("Method " + invoc.getMethodName() + " Executed.");
            run(result, service, invoc);
        }
        protected abstract void run(String result, RemoteService service, Invocation invoc);
    }
    public Invoker setCallback(Callback callback){
        this.callback = callback;

        return this;
    }
    public Invoker registerService(RemoteService service){
        repository.bind(service.getUid(), service);
        return this;
    }
    public void listen(int port) throws IOException {
        RequestHandler rh;

        ServerSocket server = new ServerSocket(port);

        while(true){
            System.out.println("Servidor Pronto\n");
            Socket sock = server.accept();

            System.out.println("New Connection");
            System.out.println(sock.toString() + "\n");

            rh = new RequestHandler(
                    sock,
                    new Request());
            Request req = rh.receive()
                            .getRequest();

            Invocation      invoc = new Requestor().mkInvocation(req);

            System.out.println("Object lookup");
            RemoteService service = repository.lookup(invoc.getUid());

            String result = "";

            if (service == null)
                result = INVALID_INVOK + "obj " + invoc.getUid() + " não existe";
            else
                result = service.call(invoc.getMethodName(), invoc.getParameters());

            callback.execute(result, service, invoc);

            rh.setRequest(req.setBody(result))
              .send();

            rh.close();

        }

    }
}
