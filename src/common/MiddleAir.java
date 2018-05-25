package common;

import auth.IAuth;
import client.cockpit.Monitor;
import common.authproxy.AuthProxy;
import common.clientproxy.ClientProxy;
import common.credentials.Credentials;
import common.namingproxy.NamingProxy;
import common.remoteservice.Service;
import names.INaming;

public class MiddleAir implements INaming, IAuth {
    NamingProxy np;
    AuthProxy auth;
    ClientProxy.Output monitor;

    public MiddleAir(String namingHost) {
        monitor = new Monitor();
        np = new NamingProxy(namingHost);
        np.setOutput(monitor);
        try {
            auth = new AuthProxy(np.lookup("auth-service"));
            auth.setOutput(monitor);

        } catch (Exception e) {
            auth = null;
        }
    }

    public boolean checkComponents() {
        return true;
    }

    @Override
    public String authenticate(Credentials person) {
        if (auth == null) {
            monitor.writeError("Impossível autenticar. Apenas comandos seguros serão permitidos.");
            return "~";
        }

        return auth.authenticate(person);
    }

    @Override
    public boolean authorize(Credentials person) {
        if (auth == null) {
            monitor.writeError("Impossível autenticar. Apenas comandos seguros serão permitidos.");
            return false;
        }

        return auth.authorize(person);
    }

    @Override
    public ClientProxy lookup(String uid) throws Exception {

        ClientProxy cp = np.lookup(uid);

        if (cp != null) {
            cp.setOutput(monitor);

            if (auth != null && auth.isAuthenticated())
                cp.addHeader("authorization", auth.getCredentials().toString());
        }

        return cp;
    }

    public INaming bind(String uid, Service service) {

        return np.bind(uid, service);
    }

    @Override
    public String[] list() {
        return np.list();
    }
}
