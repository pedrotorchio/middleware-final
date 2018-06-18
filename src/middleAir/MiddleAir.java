package middleAir;

import controllers.cockpit.components.IAuthenticationMethod;
import controllers.cockpit.components.IOutputChannel;
import middleAir.common.clientproxy.ClientProxy;
import middleAir.common.exceptions.HumanInputException;
import middleAir.common.exceptions.InvalidMethodException;
import middleAir.common.exceptions.NotFoundException;
import middleAir.common.logger.Logger;
import middleAir.common.remoteservice.Service;
import middleAir.common.types.Credentials;
import middleAir.naming.NamingProxy;
import middleAir.security.auth.AuthProxy;

public class MiddleAir extends NamingProxy{
    NamingProxy np;

    Credentials authentication;
    IOutputChannel monitor;


    public MiddleAir() {

        super("localhost");
        Logger.getSingleton()
                .shouldPrint(false)
                .save2File("middleair-logs");
    }

    public boolean checkComponents() {
        return true;
    }

    public boolean isAuthenticated(){
        return authentication != null && authentication.isAuthenticated();
    }
    public boolean hasOutputChannel(){
        return monitor != null;
    }
    public void setCredentials(Credentials authentication){
        this.authentication = authentication;
    }
    public void setOutputChannel(IOutputChannel output){
        monitor = output;
    }

    public void waitAuthentication(IAuthenticationMethod method) throws NotFoundException {
        Credentials user = null;
        while(user == null) {
            try {
                user = method.readAuthentication();
            } catch (HumanInputException e) {}
        }

        AuthProxy auth = new AuthProxy(lookup("auth-service"));
                  auth.setOutput(monitor);

        while(!user.isAuthenticated())
            user = auth.authenticate(user);

        setCredentials(user);
    }
    public ClientProxy lookup(String uid) throws NotFoundException {

        ClientProxy cp = super.lookup(uid);


        if (cp != null) {
            if(hasOutputChannel())
                cp.setOutput(monitor);
            if (isAuthenticated())
                cp.addHeader("authorization", authentication.toString());
        }

        return cp;
    }

    public MiddleAir bind(Service service) throws InvalidMethodException {

        super.bind(service);

        return this;
    }
}
