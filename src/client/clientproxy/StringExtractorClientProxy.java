package client.clientproxy;

import common.clientproxy.ClientProxy;
import stringmanipulationservices.service.IStringExtractor;

public class StringExtractorClientProxy extends ClientProxy implements IStringExtractor {

    public StringExtractorClientProxy(ClientProxy original){
        super(original);
    }
    @Override
    public String getVowels(String original){

        try {
            return call("getVowels", original);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String getConsonants(String original){

        try {
            return call("getConsonants", original);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


}
