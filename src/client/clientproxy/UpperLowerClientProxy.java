package client.clientproxy;

import common.clientproxy.ClientProxy;
import common.requestor.exceptions.InvalidMethodException;
import common.requestor.exceptions.NotFoundException;
import common.requestor.exceptions.UnauthorizedException;
import stringmanipulationservices.service.IUpperLower;

import java.io.IOException;

public class UpperLowerClientProxy extends ClientProxy implements IUpperLower {

    public UpperLowerClientProxy(ClientProxy original){
        super(original);
    }

    @Override
    public String toUppercase(String original) {
        String result = "null";
        try {
            result = call("toUppercase", original);

        } catch (InvalidMethodException e) {
            e.printStackTrace();
        } catch (NotFoundException e) {
            e.printStackTrace();
        } catch (UnauthorizedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }

    @Override
    public String toLowercase(String original) {
        String result = original;
        try {
            result = call("toLowercase", original);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            output.write(e.getMessage());
        }

        return result;
    }
}
