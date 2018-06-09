package middleAir.security.auth;

import middleAir.common.exceptions.NotFoundException;
import middleAir.common.exceptions.UnauthorizedException;
import middleAir.common.types.Credentials;

public interface IAuth {
    Credentials authenticate(Credentials person) throws UnauthorizedException, NotFoundException;
    boolean authorize(Credentials person) throws NotFoundException;
}
