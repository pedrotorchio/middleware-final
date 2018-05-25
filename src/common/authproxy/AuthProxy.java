package common.authproxy;

import auth.IAuth;
import common.clientproxy.ClientProxy;
import common.credentials.Credentials;

public class AuthProxy extends ClientProxy implements IAuth {


    boolean unsafe = false;
    Credentials credentials = null;

    public AuthProxy(ClientProxy original) {
        super(original);
    }

    public boolean isUnsafe() {
        return unsafe;
    }

    public Credentials getCredentials() {
        return credentials;
    }

    @Override
    public String authenticate(Credentials person) {
        String result = null;

        try {
            result = call("authenticate", person.uid, person.key);

            // se senha for incorreta, indicar no monitor
            if (result.indexOf("!!!PASS!!!") == 0) {
                output.write("Autenticação Inválida.");
                result = null;
            }
            // se não for possível criar hash, usamos a senha do usuário como auth key, reduzindo a segurança
            else if (result.indexOf("$$$") == 0) {
                output.write("Autenticação Insegura.");
                unsafe = true;
                result = result.substring(3, result.length());
            }

        } catch (Exception e) {
            /**
             * CASO NÃO CONSIGA AUTENTICAR NA PRIMEIRA TENTATIVA, TENTE DE NOVO.
             * DEPOIS DESISTA, POIS DEVE HAVER PROBLEMA NA INFRAESTRUTURA DE COMUNICAÇÃO OU NO SERVIDOR DE AUTENTICAÇÃO
             */
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }
            try {
                result = call("authenticate", person.uid, person.key);

            } catch (Exception e1) {
                output.write("FALHA NA AUTENTICACÃO.");
            }
        }
        if (result != null)
            credentials = new Credentials(person.uid, result);

        return result;
    }

    @Override
    public boolean authorize(Credentials person) {
        String result;
        /**
         * SE NÃO FOR POSSÍVEL VERIFICAR AUTORIZAÇÃO DO COMANDO (COMO QUANDO HOUVER ERRO DE INFRAESTRUTURA),
         * PERMITA DE QUALQUER FORMA, PARA MANTER O TEMPO DE EXECUÇÃO BAIXO
         */
        try {
            result = call("authorize", person.uid, person.key);

        } catch (Exception e) {
            return true;
        }

        return result.equals("true") || !result.equals("false");
    }

    public boolean isAuthenticated() {
        return credentials != null;
    }
}
