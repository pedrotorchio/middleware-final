package middleAir.security;


import middleAir.common.invoker.InvokerExecution;
import middleAir.naming.NamingProxy;
import middleAir.security.RSA.RSAService;
import middleAir.security.auth.AuthService;

public class SecurityServer implements Runnable{


    public static final int PORT = 5325;
    static final String HOST = "localhost";

    public static void main(String[] args) throws Exception {

        AuthService authService = new AuthService();
        authService.setPort(PORT);
        authService.setHost(HOST);

        RSAService rsaService   = new RSAService();
        rsaService.setPort(PORT);
        rsaService.setHost(HOST);

        new NamingProxy("localhost")
//                .bind(rsaService)
                .bind(authService);

        new InvokerExecution()
                .registerService(authService)
                .listen(PORT);


    }

    public void run() {
        try {
            main(new String[0]);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}