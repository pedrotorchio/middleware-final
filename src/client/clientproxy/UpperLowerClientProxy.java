package client.clientproxy;

import stringmanipulationservices.service.IUpperLower;
import common.clientproxy.ClientProxy;

public class UpperLowerClientProxy extends ClientProxy implements IUpperLower {

    public UpperLowerClientProxy(ClientProxy original){
        super(original);
    }

    @Override
    public String toUppercase(String original) {
        return call("toUppercase", original);
    }

    @Override
    public String toLowercase(String original) {
        return call("toLowercase", original);
    }
}
