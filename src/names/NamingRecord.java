package names;

import common.clientproxy.ClientProxy;

public class NamingRecord {
    protected String name;
    protected ClientProxy proxy;

    public NamingRecord(String name, ClientProxy proxy){
        this.name  = name;
        this.proxy = proxy;
    }
    public String getName(){
        return this.name;
    }
    public ClientProxy getProxy(){
        return proxy;
    }
    @Override
    public boolean equals(Object obj) {
        Class classname = obj.getClass();

        return (classname == NamingRecord.class && ((NamingRecord)obj).getName() == name) ||
               (classname == String.class       && ((String)obj == name));
    }
}
