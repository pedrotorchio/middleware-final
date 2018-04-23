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

        return call("getVowels", original);
    }

    @Override
    public String getConsonants(String original){

        return call("getConsonants", original);
    }


}
