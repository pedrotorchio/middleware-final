package names;


import common.invoker.Invoker;
import common.remoteservice.InstanceService;
import common.requestor.Invocation;

public class NamingServer {


    public static final int PORT = 9000;
    static final String HOST = "localhost";

    public static void main(String[] args){

        NamingService namingService = new NamingService();
        namingService.setPort(PORT);
        namingService.setHost(HOST);

            new Invoker()
                    .registerService(namingService)
                    .setCallback(new Invoker.Callback(){
                        protected void run(String result, InstanceService service, Invocation invoc) {
                            String message;

                            if (invoc.getMethodName().equals("bind"))
                                message = ("SERVIÇO " + result + " CADASTRADO.");
                            else if (result.equals("null"))
                                message = ("SERVIÇO NÃO ENCONTRADO! " + result);
                            else
                                message = ("SERVIÇO ENCONTRADO " + result);

                            System.out.println(message + "\n");
                        }
                    })
                    .listen(PORT);

    }
}
