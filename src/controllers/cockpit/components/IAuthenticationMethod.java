package controllers.cockpit.components;

import middleAir.common.exceptions.HumanInputException;
import middleAir.common.types.Credentials;

public interface IAuthenticationMethod {
    Credentials readAuthentication() throws HumanInputException;
}
