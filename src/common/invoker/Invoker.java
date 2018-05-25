package common.invoker;

import common.authproxy.AuthorizationInterceptor;
import common.remoteservice.InstanceService;
import common.requesthandler.Request;
import common.requesthandler.RequestHandler;
import common.requestor.Invocation;
import common.requestor.Requestor;
import common.requestor.exceptions.NotFoundException;
import common.requestor.exceptions.UnauthorizedException;
import common.servicerepository.ServiceRepository;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Invoker {

    ServiceRepository<InstanceService> repository = new ServiceRepository();

    Callback callback = new Callback(){
        protected void run(String result, InstanceService service, Invocation invoc) {
        }
    };

    public void listen(int port) {


        ServerSocket server = null;
        try {
            server = new ServerSocket(port);

        } catch (IOException e) {
            e.printStackTrace();
        }

        while (true) {
            System.out.println("Servidor Pronto\n");

            RequestHandler rh = waitConnection(server);

            if (rh != null) {

                Request req = receiveRequest(rh);

                req = executeRequestedService(req);

                sendRequest(rh, req);
            }
        }
    }

    private void sendRequest(RequestHandler rh, Request req) {

        try {
            rh.setRequest(req)
                    .send();

        } catch (IOException e) {
            e.printStackTrace();
        }

        rh.close();

    }

    private RequestHandler waitConnection(ServerSocket server) {
        Socket sock = null;
        RequestHandler rh = null;
        try {
            sock = server.accept();

            rh = new RequestHandler(
                    sock,
                    new Request());

            System.out.println("New Connection");
            System.out.println(sock.toString() + "\n");

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

    protected Request receiveRequest(RequestHandler rh) {
        Request req;

        try {
            req = rh.receive()
                    .getRequest();

        } catch (IOException e) {
            req = new Request()
                    .addHeader("error", "500")
                    .setBody("Erro de socket");
        }

        return req;
    }

    protected Request executeRequestedService(Request req) {

        Invocation invoc;
        InstanceService service;
        String result = "";

        invoc = new Requestor().mkInvocation(req);

        System.out.println("Object lookup");
        service = repository.lookup(invoc.getUid());

        if (service == null) {
            req.addHeader("error", NotFoundException.CODE);
            req.setBody("Objeto invocado (" + invoc.getUid() + ") não existe");
        } else if (!service.isProtected() || authorizeRequest(req))
            req = service.execute(req, invoc.getMethodName(), invoc.getParameters());

        callback.execute(result, service, invoc);


        return req;
    }

    protected boolean authorizeRequest(Request req) {
        try {

            req = new AuthorizationInterceptor().intercept(req);
            req.addHeader("authorization", "OK");

        } catch (UnauthorizedException e) {
            // nao autorizado
            req.setBody(e.getMessage());
            req.addHeader("error", UnauthorizedException.CODE);
            req.addHeader("authorization", "DENIED");

            return false;
        }

        return true;
    }


    public abstract static class Callback {
        public void execute(String result, InstanceService service, Invocation invoc) {
            System.out.println("Method " + invoc.getMethodName() + " Executed.");
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
