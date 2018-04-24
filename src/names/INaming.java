package names;

import common.clientproxy.ClientProxy;
import common.remoteservice.RemoteService;
import common.servicerepository.ServiceRepository;

import java.net.UnknownHostException;

public interface INaming {
    <T extends RemoteService> T lookup(String uid);
    INaming bind(String uid, RemoteService service);
    String[] list() throws UnknownHostException;
}
