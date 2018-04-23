package client.clientproxy;

import java.rmi.UnknownHostException;

public class StringReorderClientProxy extends ClientProxy implements IStringReorder {
    @Override
    public String lexicographical(String original) throws UnknownHostException {
        return null;
    }

    @Override
    public String vowelsFirst(String original) throws UnknownHostException {
        return null;
    }

    @Override
    public String consonantsFirst(String original) throws UnknownHostException {
        return null;
    }
}
