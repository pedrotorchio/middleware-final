package auth;

import common.credentials.Credentials;
import common.remoteservice.InstanceService;
import common.remoteservice.RemoteService;
import common.requesthandler.Request;
import common.requestor.exceptions.InternalErrorException;
import common.requestor.exceptions.NotFoundException;
import common.requestor.exceptions.UnauthorizedException;
import common.servicerepository.ServiceRepository;
import org.jasypt.util.text.BasicTextEncryptor;

import java.util.Hashtable;

public class AuthService extends InstanceService implements IAuth {

    private String PASS = "senhadovoo";

    Hashtable<String, String> authKeys = new Hashtable();
    protected static BasicTextEncryptor te = null;
    ServiceRepository repository = new ServiceRepository();

    public AuthService() {
        uid = "auth-service";
    }

    public <T extends RemoteService> T lookup(String uid) {
        return (T) repository.lookup(uid);
    }

    public String authenticate(Credentials person) throws UnauthorizedException {
        String authKey = null,
                result = null;

        if (!person.key.equals(PASS))
            throw new UnauthorizedException();

        try {
            authKey = makeAuthKey(person.uid, person.key);
        } catch (Exception e) {
            authKey = person.key;
        }

        result = authKey;


        System.out.println("Authenticating.. ");
        System.out.println("\t\t" + person.uid + " " + authKey + "\n");

        authKeys.put(person.uid, authKey);

        return result;
    }

    public void setPassword(String password) {
        PASS = password;
    }

    public static String makeAuthKey(String uid, String key) {
        te = new BasicTextEncryptor();
        te.setPassword(key);
        return te.encrypt(uid + key + System.currentTimeMillis());
    }

    public boolean authorize(Credentials person) {
        System.out.println("Authorizing.. ");
        System.out.println("\t\t" + person.uid + " " + person.key + "\n");

        return person.key.equals(authKeys.get(person.uid));
    }

    protected String call(String name, String... parameters) throws UnauthorizedException, NotFoundException {
        String result = "";
        String arg0, arg1;

        switch (name) {
            case "authenticate":

                arg0 = parameters[0];
                arg1 = parameters[1];

                result = authenticate(new Credentials(arg0, arg1));

                break;

            case "authorize":

                arg0 = parameters[0];
                arg1 = parameters[1];

                result = authorize(new Credentials(arg0, arg1)) ? "true" : "false";
                break;

            default:
                throw new NotFoundException("Método não existe.");

        }

        return result;
    }

    public Request execute(Request req, String methodname, String... parameters) {
        String result = "";
        try {

            result = call(methodname, parameters);

        } catch (UnauthorizedException e) {
            req.addHeader("authorization", UnauthorizedException.CODE);
            result = "Senha incorreta.";
        } catch (NotFoundException e) {
            req.addHeader("error", NotFoundException.CODE);
            result = e.getMessage();
        } catch (Exception e) {
            req.addHeader("error", InternalErrorException.CODE);
            result = e.getMessage();
        }

        return req.setBody(result);
    }

    public boolean isProtected() {
        return false;
    }
}
