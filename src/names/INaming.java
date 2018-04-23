package names;

import client.clientproxy.ClientProxy;

import java.net.UnknownHostException;

public interface INaming {
    void bind(String name, ClientProxy proxy) throws UnknownHostException;
    ClientProxy lookup(String name) throws UnknownHostException;
    String[] list() throws UnknownHostException;
}
