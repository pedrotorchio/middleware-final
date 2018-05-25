package stringmanipulationservices.service;

import common.remoteservice.InstanceService;
import common.requesthandler.Request;

public class UpperLowerService extends InstanceService implements IUpperLower {

    public UpperLowerService(){
        uid = "upper-lower";
    }

    @Override
    public String toUppercase(String original) {
        return original.toUpperCase();
    }

    @Override
    public String toLowercase(String original) {
        return original.toLowerCase();
    }


    public Request execute(Request req, String name, String... args) {
        String result = "";

        switch(name){
            case "toUppercase":
                result = toUppercase(args[0]);
                break;
            case "toLowercase":
                result = toLowercase(args[0]);
                break;
        }

        req.setBody(result);

        return req;
    }

    public boolean isProtected() {
        return true;
    }
}
