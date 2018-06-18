package cockpit;

import middleAir.common.logger.Logger;

import java.util.Scanner;

public class App {

    static Cockpit pit;
    static Scanner in = new Scanner(System.in);
    public static void main(String[] args) throws InterruptedException {

        System.out.println("<enter> para iniciar teclado de autenticação");
        in.nextLine();
        System.out.println("Verifique o teclado");

        pit = new Cockpit();
        start();

    }
    private static int[] getCommand(){

        String[] command = in.nextLine().split(" ");
        int commC = command[0].charAt(0);
        int value = command.length > 1 ? Integer.parseInt(command[1]) : -1;

        int[] comm = {commC, value};

        return comm;
    }
    private static void start(){
        while(!pit.setup());

        do{

            int[] command = commandMenu();
            int c = command[0],
                v = command[1];

            switch(c){
                case 'q':
                    if(v == -1)
                        v = 500;
                    break;
                case 'e':
                    if(v == -1)
                        v = 1000;
                    break;
                case 'a':
                case 's':
                case 'f':
                case 'w':
                    if(v == -1)
                        v = 10;
            }
            switch(c){
                case 'n':
                    pit.on();
                    break;
                case 'm':
                    pit.off();
                    break;
                case 'q':
                    pit.powerDown(v);
                    break;
                case 'e':
                    pit.powerUp(v);
                    break;
                case 'w':
                    pit.rise(-v);
                    break;
                case 's':
                    pit.rise(v);
                    break;
                case 'a':
                    pit.steer(-v);
                    break;
                case 'd':
                    pit.steer(v);
                    break;
            }

        }while(true);
    }
    private static int[] commandMenu(){
        Logger.getSingleton().println("Comandos ( em minúsculo )",
               " N - Ligar\n" +
                        " M - Desligar\n\n" +
                        " Q  <num (500pwr)>  - Reduzir Potência\n" +
                        " E  <num (1000pwr)> - Aumentar Potência\n" +
                        " W  <num (10deg)>   - Navegação\n" +
                        "ASD\n"

        );
        return getCommand();
    }
}
