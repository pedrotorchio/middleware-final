package StringExtractorService;

import StringExtractorService.service.StringExtractorService;
import common.invoker.Invoker;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class Server {
    static InetAddress host;
    static int port = 8609;

    public static void main(String[] args){

        // criar instancia do serviço

        // registrar nome
        // registrar instancia
        // iniciar servidor

        try {
            new Invoker()
                    .registerService(new StringExtractorService())
                    .listen(port);


        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
