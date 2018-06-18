package middleAir.common.invoker;

import middleAir.MiddleAir;
import middleAir.common.exceptions.UnauthorizedException;
import middleAir.common.interceptor.IInterceptor;
import middleAir.common.logger.Logger;
import middleAir.common.requesthandler.Request;
import middleAir.common.types.Credentials;
import middleAir.security.auth.AuthProxy;

import java.lang.reflect.MalformedParametersException;

public class AuthorizationInterceptor implements IInterceptor<Request, UnauthorizedException> {

    public Request intercept(Request req) throws UnauthorizedException {
        MiddleAir middleair = new MiddleAir();
        AuthProxy auth;
        try {
             auth = new AuthProxy(middleair.lookup("auth-service"));

        } catch (Exception e) {
            throw new UnauthorizedException("Serviço de Autorização inalcançável");
        }

        Logger.getSingleton().println("Checking Permission..");

        if (!req.getHeader().containsKey("authorization"))
            throw new UnauthorizedException("Header authorization faltando.");

        Credentials person;
        try {
            person = Credentials.fromString(req.getHeader("authorization"));

        }catch(MalformedParametersException e){
            throw new UnauthorizedException("Autorização inválida.");
        }

        if (!auth.authorize(person))
            throw new UnauthorizedException(person);

        return req;
    }
}
