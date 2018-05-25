package common.remoteservice;

import common.requestor.exceptions.InvalidMethodException;
import common.requestor.exceptions.NotFoundException;
import common.requestor.exceptions.UnauthorizedException;

import java.io.IOException;

/**
 * CLIENT PROXY RECEBE CHAMADAS A METODOS REMOTOS COM ASSINATURA ESPECÍFICA
 */

public abstract class RemoteService extends Service {


    public abstract String call(String name, String... parameters)
            throws
            InvalidMethodException,
            NotFoundException,
            UnauthorizedException,
            IOException;


}
