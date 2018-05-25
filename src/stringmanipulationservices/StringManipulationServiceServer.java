package stringmanipulationservices;

import common.invoker.Invoker;
import common.namingproxy.NamingProxy;
import common.remoteservice.InstanceService;
import common.requestor.Invocation;
import stringmanipulationservices.service.StringExtractorService;
import stringmanipulationservices.service.UpperLowerService;

public class StringManipulationServiceServer {

    static final int PORT = 8609;
    static final String HOST = "localhost";

    public static void main(String[] args) throws Exception {

        // criar instancia do serviço
        InstanceService strExt = new StringExtractorService();
        strExt.setHost(HOST);
        strExt.setPort(PORT);
        InstanceService uplow = new UpperLowerService();
        uplow.setHost(HOST);
        uplow.setPort(PORT);

            // registrar nome
            new NamingProxy("localhost")
                    .bind(strExt.getUid(), strExt)
                    .bind(uplow.getUid(), uplow);


            new Invoker()

                    // registrar instancia
                    .registerService(strExt)
                    .registerService(uplow)
                    .setCallback(new Invoker.Callback(){
                        public void run(String result, InstanceService service, Invocation invoc) {
                            System.out.println(invoc.getMethodName() + "." + invoc.getUid() + " = " + result + "\n");
                        }
                    })
                    // iniciar servidor
                    .listen(PORT);



    }
}
