package stringmanipulationservices.service;

import common.remoteservice.RemoteService;

public class UpperLowerService extends RemoteService implements IUpperLower {

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

    @Override
    public String call(String name, String... parameters) {
        String result = "";
        switch(name){
            case "toUppercase":
                result = toUppercase(parameters[0]);
                break;
            case "toLowercase":
                result = toUppercase(parameters[0]);
                break;
        }
        return result;
    }
}
