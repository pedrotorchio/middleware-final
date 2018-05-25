package names;

import common.remoteservice.RemoteService;
import common.remoteservice.Service;

public interface INaming {
    <T extends RemoteService> T lookup(String uid) throws Exception;

    INaming bind(String uid, Service service) throws Exception;

    String[] list() throws Exception;
}
