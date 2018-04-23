package client.clientproxy;

import StringExtractorService.service.IStringExtractor;
import common.requestor.Invocation;
import common.requestor.Requestor;
import common.clientproxy.ClientProxy;

import java.io.IOException;
import java.util.ArrayList;

public class StringExtractorClientProxy extends ClientProxy implements IStringExtractor {
    @Override
    public String getVowels(String original){

        ArrayList<String> parameters = new ArrayList(1);
                          parameters.add(original);
        Invocation invoc = prepareInvocation("getVowels", parameters);

        return new Requestor()
                        .invoke(invoc)
                            .getResult();
    }

    @Override
    public String getConsonants(String original){
        ArrayList<String> parameters = new ArrayList(1);
        parameters.add(original);
        Invocation invoc = prepareInvocation("getConsonants", parameters);

        return new Requestor()
                        .invoke(invoc)
                            .getResult();
    }


}
