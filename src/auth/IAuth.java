package auth;

import common.credentials.Credentials;

public interface IAuth {
    String authenticate(Credentials person) throws Exception;

    boolean authorize(Credentials person) throws Exception;
}
