package names;


import common.invoker.Invoker;
import common.remoteservice.RemoteService;
import common.requestor.Invocation;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class NamingServer {

    static String HOST;

    static {
        try {
            HOST = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    public static final int PORT = 9000;

    public static void main(String[] args){

        RemoteService namingService = new NamingRepository()
                .setPort(PORT)
                .setHost(HOST);
        try {

            new Invoker()
                    .registerService(namingService)
                    .setCallback(new Invoker.Callback(){
                        protected void run(String result, RemoteService service, Invocation invoc) {
                            String message = "";

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

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
