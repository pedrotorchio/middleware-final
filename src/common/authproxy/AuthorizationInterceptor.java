package common.authproxy;

import common.MiddleAir;
import common.credentials.Credentials;
import common.interceptor.IInterceptor;
import common.requesthandler.Request;
import common.requestor.exceptions.UnauthorizedException;

public class AuthorizationInterceptor implements IInterceptor<Request, UnauthorizedException> {

    public Request intercept(Request req) throws UnauthorizedException {
        MiddleAir middleair = new MiddleAir("localhost");

        System.out.println("Checking Permission..");

        if (!req.getHeader().containsKey("authorization"))
            throw new UnauthorizedException("Header authorization faltando.");

        String[] credentials = req.getHeader("authorization").split(" ", 2);

        if (credentials.length == 2) {
            Credentials person = new Credentials(credentials[0], credentials[1]);

            if (!middleair.authorize(person))
                throw new UnauthorizedException(person);
        } else
            throw new UnauthorizedException("Autorização inválida.");

        return req;
    }
}
