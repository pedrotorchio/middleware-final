package auth;

import java.util.Scanner;

public class PasswordInput {

    public String readPassword() {
        Scanner in = new Scanner(System.in);

        System.out.print("Defina senha do próximo vôo: ");
        String senha = in.nextLine();

        return senha;
    }
}
