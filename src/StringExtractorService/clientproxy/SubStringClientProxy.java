package StringExtractorService.clientproxy;

import StringExtractorService.requestor.Invocation;
import StringExtractorService.requestor.Requestor;

import java.io.IOException;
import java.util.ArrayList;

public class SubStringClientProxy extends ClientProxy implements ISubString {
    @Override
    public String getVowels(String original) throws IOException {

        ArrayList<Object> parameters = new ArrayList(1);
                          parameters.add(original);
        Invocation invoc = prepareInvocation("getVowels", parameters);

        return new Requestor()
                        .invoke(invoc)
                            .getResult();
    }

    @Override
    public String getConsonants(String original) throws IOException {
        ArrayList<Object> parameters = new ArrayList(1);
        parameters.add(original);
        Invocation invoc = prepareInvocation("getConsonants", parameters);

        return new Requestor()
                        .invoke(invoc)
                            .getResult();
    }


}
