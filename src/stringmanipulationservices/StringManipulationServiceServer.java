package stringmanipulationservices;

import stringmanipulationservices.service.StringExtractorService;
import stringmanipulationservices.service.UpperLowerService;
import common.invoker.Invoker;
import common.remoteservice.RemoteService;
import common.requestor.Invocation;
import names.NamingProxy;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class StringManipulationServiceServer {
    static String HOST;

    static {
        try {
            HOST = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    static int PORT = 8609;

    public static void main(String[] args){

        // criar instancia do serviço
        RemoteService strExt = new StringExtractorService()
                .setHost(HOST)
                .setPort(PORT);
        RemoteService uplow  = new UpperLowerService()
                .setHost(HOST)
                .setPort(PORT);
        try {
            // registrar nome
            new NamingProxy("localhost")
                    .bind(strExt.getUid(), strExt)
                    .bind(uplow.getUid(), uplow);

            new Invoker()
                    // registrar instancia
                    .registerService(strExt)
                    .registerService(uplow)
                    .setCallback(new Invoker.Callback(){
                        public void run(String result, RemoteService service, Invocation invoc) {
                            System.out.println(invoc.getMethodName() + "." + invoc.getUid() + " = " + result + "\n");
                        }
                    })
                    // iniciar servidor
                    .listen(PORT);


        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
