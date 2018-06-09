package middleAir.naming;

import middleAir.common.exceptions.InvalidMethodException;
import middleAir.common.exceptions.NotFoundException;
import middleAir.common.remoteservice.RemoteService;
import middleAir.common.remoteservice.Service;

interface INaming {
    RemoteService lookup(String uid) throws NotFoundException;

    INaming bind(Service service) throws InvalidMethodException;
}
