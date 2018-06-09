package middleAir.common.remoteservice;

import middleAir.common.exceptions.*;

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
            IOException, InternalErrorException, HumanInputException, TimeoutException;


}
