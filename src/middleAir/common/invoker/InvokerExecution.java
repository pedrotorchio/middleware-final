package middleAir.common.invoker;

import middleAir.common.exceptions.NotFoundException;
import middleAir.common.exceptions.UnauthorizedException;
import middleAir.common.logger.Logger;
import middleAir.common.remoteservice.InstanceService;
import middleAir.common.requesthandler.Request;
import middleAir.common.requesthandler.RequestHandler;
import middleAir.common.requestor.Invocation;
import middleAir.common.requestor.Requestor;

import java.io.IOException;

public class InvokerExecution extends Invoker{
    protected void receiveRequest(RequestHandler rh) {

        new Executor(rh).start();

    }

    // THREADS

    protected class Executor extends Thread{

        RequestHandler rh;
        InstanceService service;
        Request req;
        boolean hasTO = false;

        public Executor(RequestHandler rh){
            this.rh = rh;
        }

        public void run(){

            try {
                req = rh.receive()
                        .getRequest();

                req = rh.getRequest();
                req = executeRequestedService(req);

                sendRequest(req);

            } catch (IOException e) {
                req = new Request()
                        .addHeader("error", "500")
                        .setBody("Erro de socket");

                sendRequest(req);

            }
        }
        protected void sendRequest(Request req){
            try {
                if(!rh.isClosed())
                    rh.setRequest(req)
                            .send();
            } catch (IOException e) {
                e.printStackTrace();
            }

            rh.close();
        }
        protected Request executeRequestedService(Request req) {

            Invocation invoc;
            String result = "";

            invoc = new Requestor().mkInvocation(req);

            Logger.getSingleton().maybePrintln("Object Lookup");
            service = repository.lookup(invoc.getUid());

            if (service == null)

                req = new NotFoundException("Objeto invocado (" + invoc.getUid() + ")").interceptRequest(req);

            else if (!service.isProtected() || authorizeRequest(req))

                req = executeOrInterrupt(req, invoc);


            callback.execute(result, service, invoc);

            return req;
        }

        protected Request executeOrInterrupt(Request req, Invocation invoc){
            hasTO = req.getHeader().containsKey("timeout");

            Clock clock = new Clock();

            if(hasTO)
                clock.start();

            req.addHeader("method", invoc.getMethodAOR());
            req = service.execute(req, invoc.getMethodName(), invoc.getParameters());

            clock.interrupt();

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

        private class Clock extends Thread{

            public static final long INTERVAL = 500; // meio segundo
            protected TimeoutInterceptor ti = new TimeoutInterceptor();;

            public void run(){
                Time toTime = ti.getTime(req);

                while(!isInterrupted() && !ti.hasPassed(toTime)){
                    try {
                        Thread.sleep(INTERVAL);

                    } catch (InterruptedException e) {
                        break;
                    }
                }
                if(!isInterrupted())
                    service.interrupt();

            }
        }
    }
}
