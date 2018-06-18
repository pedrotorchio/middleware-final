package middleAir.common.invoker;

import middleAir.common.logger.Logger;
import middleAir.common.remoteservice.InstanceService;
import middleAir.common.requesthandler.Request;
import middleAir.common.requesthandler.RequestHandler;
import middleAir.common.requestor.Invocation;
import middleAir.common.servicerepository.ServiceRepository;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public abstract class Invoker {

    ServiceRepository<InstanceService> repository = new ServiceRepository();

    Callback callback = new Callback(){
        protected void run(String result, InstanceService service, Invocation invoc) {}
    };

    public void listen(int port) {


        ServerSocket server = null;
        try {
            server = new ServerSocket(port);

        } catch (IOException e) {
            e.printStackTrace();
        }

        while (true) {
            Logger.getSingleton().println("Servidor Pronto");

            RequestHandler rh = waitConnection(server);

            if (rh != null)
                receiveRequest(rh);

        }
    }

    private RequestHandler waitConnection(ServerSocket server) {
        Socket sock = null;
        RequestHandler rh = null;
        try {
            sock = server.accept();

            rh = new RequestHandler(
                    sock,
                    new Request());

            Logger.getSingleton().maybePrintln("New Connection", sock.toString()).log();

        } catch (IOException e) {
            if (sock != null) {
                try {
                    sock.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            if (rh != null)
                rh.close();
        }

        return rh;
    }
    protected abstract void receiveRequest(RequestHandler rh);

    public abstract static class Callback {
        public void execute(String result, InstanceService service, Invocation invoc) {
            Logger.getSingleton().maybePrintln("Method " + invoc.getMethodName() + " Executed.").log();
            run(result, service, invoc);
        }

        protected abstract void run(String result, InstanceService service, Invocation invoc);
    }

    public Invoker setCallback(Callback callback) {
        this.callback = callback;

        return this;
    }

    public Invoker registerService(InstanceService service) {
        repository.bind(service.getUid(), service);

        return this;
    }

}
