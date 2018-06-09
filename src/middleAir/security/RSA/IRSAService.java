package middleAir.security.RSA;

import middleAir.common.exceptions.NotFoundException;

public interface IRSAService {
    String getPublicKey(String uid) throws NotFoundException;
    String setPublicKey(String uid, String key);
}
