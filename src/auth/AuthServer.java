package auth;


import common.invoker.Invoker;
import common.namingproxy.NamingProxy;

public class AuthServer {


    public static final int PORT = 5325;
    static final String HOST = "localhost";

    public static void main(String[] args) {

        AuthService authService = new AuthService();
        authService.setPort(PORT);
        authService.setHost(HOST);
        authService.setPassword(new PasswordInput().readPassword());

        new NamingProxy("localhost")
                .bind(authService.getUid(), authService);

        new Invoker()
                .registerService(authService)
                .listen(PORT);


    }
}
