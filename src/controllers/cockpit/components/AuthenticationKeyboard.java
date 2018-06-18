package controllers.cockpit.components;

import middleAir.common.exceptions.HumanInputException;
import middleAir.common.exceptions.MiddleAirException;
import middleAir.common.logger.Logger;
import middleAir.common.remoteservice.InstanceService;
import middleAir.common.requesthandler.Request;
import middleAir.common.types.Credentials;

import java.util.Scanner;

public class AuthenticationKeyboard extends InstanceService implements IAuthenticationMethod {

    public AuthenticationKeyboard(){
        uid = "auth-keyboard";
    }
    public Credentials readAuthentication() throws HumanInputException {
        Scanner in = new Scanner(System.in);

        Logger.getSingleton().println(
                "Teclado de Autenticação",
                "Digite Credenciais ou a palavra 'cancelar'");

        String [] asks  = {"Piloto", "Senha"};
        String [] creds = new String[2];

        for(int i = 0 ; i < 2 ; i++){
            Logger.getSingleton().print(asks[i] + ": ");
            creds[i] = in.nextLine();

            if(creds[i].equals("cancelar"))
                throw new HumanInputException("Autenticação Cancelada.");
        }

        Credentials person = new Credentials(creds[0], creds[1]);

        return person;
    }

    public String call(Request req, String methodname, String[] parameters) throws MiddleAirException {


        String angle = parameters[0];

        switch (methodname) {
            case "readAuthentication":
                Credentials person = readAuthentication();

                    return Credentials.serialize(person);

            default:
                return "DEFAULT";
        }

    }

    public boolean isProtected() {
        return false;
    }
}
