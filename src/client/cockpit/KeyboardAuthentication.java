package client.cockpit;

import common.credentials.Credentials;
import common.credentials.IAuthenticationMethod;

import java.util.Scanner;

public class KeyboardAuthentication implements IAuthenticationMethod {

    public Credentials readAuthentication() {
        Scanner in = new Scanner(System.in);

        System.out.print("Piloto: ");
        String uid = in.nextLine();

        System.out.print("Senha: ");
        String pass = in.nextLine();

        Credentials person = new Credentials(uid, pass);

        return person;
    }
}
