package middleAir.security.auth;

import middleAir.common.exceptions.MiddleAirException;
import middleAir.common.exceptions.NotFoundException;
import middleAir.common.exceptions.UnauthorizedException;
import middleAir.common.remoteservice.InstanceService;
import middleAir.common.requesthandler.Request;
import middleAir.common.types.Credentials;
import middleAir.security.common.BasicTextEncryptor;

import java.util.Hashtable;

//import org.jasypt.util.text.BasicTextEncryptor;

public class AuthService extends InstanceService implements IAuth{

    private String PASS = "senhadovoo";

    Hashtable<String, String> authKeys = new Hashtable();
    Hashtable<String, String> register = new Hashtable() {{
        put("pedro", "161091");
        put("ze", "12345");
        put("igor", "456654");
    }};

    protected static BasicTextEncryptor te = null;


    public AuthService() {
        uid = "auth-service";
    }



    public Credentials authenticate(Credentials person) throws UnauthorizedException, NotFoundException {
        String authKey;

        if(!register.containsKey(person.uid))
            throw new NotFoundException(person.uid + " não existe.");

        if(!register.get(person.uid).equals(person.key))
            throw new UnauthorizedException("Senha incorreta.");

        try {
            authKey = makeAuthKey(person.uid, person.key);
        } catch (Exception e) {
            authKey = person.key;
        }

        System.out.println("Authenticating.. ");
        System.out.println("\t\t" + person.uid + " " + authKey + "\n");

        person.authKey = authKey;
        authKeys.put(person.uid, authKey);

        return person;
    }

    public static String makeAuthKey(String uid, String key) {
        te = new BasicTextEncryptor();
        te.setPassword(key);
        return te.encrypt(uid + key + System.currentTimeMillis());
    }

    public boolean authorize(Credentials person) throws NotFoundException {
        System.out.println("Authorizing.. ");
        System.out.println("\t\t" + person.uid + " " + person.key + "\n");

        if(!register.containsKey(person.uid))
            throw new NotFoundException(person.uid + " não existe.");

        return person.authKey.equals(authKeys.get(person.uid));
    }

    public String call(Request req, String methodname, String[] parameters) throws MiddleAirException{


        String uid = parameters[0];
        String key = parameters[1];
        String result;

        switch (methodname) {
            case "authenticate":
                result = authenticate(new Credentials(uid, key)).authKey;
                setReturnMeaning(req, "Authkey para usuario");
                return result;

            case "authorize":

                Credentials cred = new Credentials(uid, null);
                cred.authKey = key;

                result = authorize(cred) ? "true" : "false";
                setReturnMeaning(req, "SE usuario foi autorizado");

                return result;

            default:
                return "DEFAULT";
        }

    }

    public boolean isProtected() {
        return false;
    }
}
