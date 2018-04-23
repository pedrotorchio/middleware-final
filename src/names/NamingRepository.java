package names;

import common.clientproxy.ClientProxy;

import java.net.UnknownHostException;
import java.util.ArrayList;

public class NamingRepository implements INaming{
    protected ArrayList<NamingRecord> repository = new ArrayList();

    @Override
    public void bind(String name, ClientProxy proxy) throws UnknownHostException {
        repository.add(new NamingRecord(name, proxy));
    }

    @Override
    public ClientProxy lookup(String name) throws UnknownHostException {
        return repository.get(repository.indexOf(name)).getProxy();
    }

    @Override
    public String[] list() throws UnknownHostException {
        return repository.stream().map(record -> record.getName()).toArray(size -> new String[size]);
    }
}
