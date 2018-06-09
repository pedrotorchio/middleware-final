package middleAir.common.invoker;

import middleAir.common.exceptions.NotFoundException;
import middleAir.common.exceptions.TimeoutException;
import middleAir.common.exceptions.UnauthorizedException;
import middleAir.common.remoteservice.InstanceService;
import middleAir.common.requesthandler.Request;
import middleAir.common.requesthandler.RequestHandler;
import middleAir.common.requestor.Invocation;
import middleAir.common.requestor.Requestor;

import java.io.IOException;

public class InvokerExecution extends Invoker{

    protected Request receiveRequest(RequestHandler rh) {
        Request req;

        try {
            req = rh.receive()
                    .getRequest();

            new Executor(rh).start();

            if(req.getHeader().containsKey("timeout"))
                new Clock(rh).start();

        } catch (IOException e) {
            req = new Request()
                    .addHeader("error", "500")
                    .setBody("Erro de socket");

            sendRequest(rh, req);
        }

        return req;
    }

    // THREADS

    protected class ParallelExecutions extends Thread{
        RequestHandler rh;

        public ParallelExecutions(RequestHandler rh){
            this.rh = rh;
        }

    }

    protected class Clock extends ParallelExecutions{
        public static final long INTERVAL = 500; // meio segundo
        protected TimeoutInterceptor ti;
        public Clock(RequestHandler rh){
            super(rh);
            ti = new TimeoutInterceptor();
        }
        public void run(){
            Request req = rh.getRequest().clone();

            Time toTime = ti.getTime(req);

            while(!ti.hasPassed(toTime)){
                try {
                    Thread.sleep(INTERVAL);

                } catch (InterruptedException e) {
                    break;
                }
            }

            req = new TimeoutException("COLOCAR TEMPO AQUI").interceptRequest(req);

            sendRequest(rh, req);
        }

    }
    protected class Executor extends ParallelExecutions {


        public Executor(RequestHandler rh){
            super(rh);
        }

        public void run(){
            Request req = rh.getRequest().clone();
                    req = executeRequestedService(req);

                    sendRequest(rh, req);
        }

        protected Request executeRequestedService(Request req) {

            Invocation invoc;
            InstanceService service;
            String result = "";

            invoc = new Requestor().mkInvocation(req);

            System.out.println("Object lookup");
            service = repository.lookup(invoc.getUid());

            if (service == null) {

                req = new NotFoundException("Objeto invocado (" + invoc.getUid() + ")").interceptRequest(req);

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
                req = e.interceptRequest(req);

                return false;
            }

            return true;
        }
    }

}
