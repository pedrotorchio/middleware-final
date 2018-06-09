package middleAir.security.auth;

import middleAir.common.clientproxy.ClientProxy;
import middleAir.common.types.Credentials;

public class AuthProxy extends ClientProxy implements IAuth {

    Credentials credentials = null;

    public AuthProxy(ClientProxy original) {
        super(original);
    }

    public Credentials getCredentials() {
        return credentials;
    }

    public Credentials authenticate(Credentials person){
        String result = null;


        try {
            result = call("authenticate", person.uid, person.key);
            person.setAuthKey(result);

        } catch (Exception e) {
            writeError(e.getMessage());

        }

        return person;
    }

    public boolean authorize(Credentials person) {
        String result = "";
        /**
         * SE NÃO FOR POSSÍVEL VERIFICAR AUTORIZAÇÃO DO COMANDO (COMO QUANDO HOUVER ERRO DE INFRAESTRUTURA),
         *
         * PERMITA DE QUALQUER FORMA, PARA MANTER O TEMPO DE EXECUÇÃO BAIXO OU
         * NEGUE ACESSO
         */


        try {

            result = call("authorize", person.uid, person.authKey);

        } catch (Exception e) {
            result = "false";
        }


        return result.equals("true");
    }

    public boolean isAuthenticated() {
        return credentials != null;
    }
}
