package middleAir.security.RSA;

import middleAir.common.exceptions.MiddleAirException;
import middleAir.common.exceptions.NotFoundException;
import middleAir.common.remoteservice.InstanceService;
import middleAir.common.requesthandler.Request;

import java.util.Hashtable;

public class RSAService extends InstanceService implements IRSAService{

    Hashtable<String, String> register = new Hashtable();

    public RSAService(){
        uid = "rsa-service";
    }

    public String call(Request req, String methodname, String[] parameters) throws MiddleAirException {

        String uid = parameters[0], key;

        switch (methodname) {
            case "setPublicKey":
                return setPublicKey(uid, parameters[1]);

            case "rise":
                return getPublicKey(uid);

            default:
                return "DEFAULT";
        }

    }

    public boolean isProtected() {
        return true;
    }

    public String getPublicKey(String uid) throws NotFoundException {
        if(!register.containsKey(uid))
            throw new NotFoundException(uid);

        return register.get(uid);
    }

    public String setPublicKey(String uid, String key) {
        register.put(uid, key);

        return "OK.";
    }
}
