package middleAir.naming;


import middleAir.common.invoker.InvokerExecution;
import middleAir.common.logger.Logger;
import middleAir.common.remoteservice.InstanceService;
import middleAir.common.requestor.Invocation;

class NamingServer implements Runnable {


    public static final int PORT = 9000;
    static final String HOST = "localhost";

    public static void main(String[] args){

        NamingService namingService = new NamingService();
        namingService.setPort(PORT);
        namingService.setHost(HOST);

            new InvokerExecution()
                    .registerService(namingService)
                    .setCallback(new InvokerExecution.Callback(){
                        protected void run(String result, InstanceService service, Invocation invoc) {

                            if (invoc.getMethodName().equals("bind"))
                                Logger.getSingleton().println("SERVIÇO " + result + " CADASTRADO.").log();

                        }
                    })
                    .listen(PORT);

    }

    public void run() {
        main(new String[0]);
    }
}
