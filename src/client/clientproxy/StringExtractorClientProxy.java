package client.clientproxy;

import stringmanipulationservices.service.IStringExtractor;
import common.clientproxy.ClientProxy;

public class StringExtractorClientProxy extends ClientProxy implements IStringExtractor {

    public StringExtractorClientProxy(ClientProxy original){
        super(original);
    }
    @Override
    public String getVowels(String original){

        return call("getVowels", original);
    }

    @Override
    public String getConsonants(String original){

        return call("getConsonants", original);
    }


}
