package controllers.cockpit.proxies;

import controllers.cockpit.components.IAuthenticationMethod;
import middleAir.common.clientproxy.ClientProxy;
import middleAir.common.invoker.Time;
import middleAir.common.types.Credentials;

public class AuthenticationKeyboardProxy extends ClientProxy implements IAuthenticationMethod {

    public AuthenticationKeyboardProxy(ClientProxy original){
        super(original);
    }
    public Credentials readAuthentication(){
        addHeader("timeout", Time.secondsLater(10).toString());

        String result;
        Credentials person = null;

        try {
            result = call("readAuthentication");
            person = Credentials.deserialize(result);

        } catch (Exception e) {
            // se usuario cancelar (ou ocorrer algum erro), retornar null
        }

        return person;
    }
}
