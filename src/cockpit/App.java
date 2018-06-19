package cockpit;

import middleAir.common.logger.Logger;

import java.util.Date;
import java.util.Scanner;

public class App {

    static Cockpit pit;
    static Scanner in = new Scanner(System.in);
    public static void main(String[] args) throws InterruptedException {

        pit = new Cockpit();
        start();

    }
    private static int[] getCommand(){

        String line = in.nextLine();

        int[] comm = {'\n', -1, -1};

        if(line.length() == 0)
            return comm;

        String[] command = line.split(" ");

        int commC = command[0].charAt(0);

        int value = -1;

        if (command.length == 2) {
            value = Integer.parseInt(command[1]);

        } else if (command.length > 2) {
            value = command[1].charAt(0);
            int repeat = Integer.parseInt(command[2]);
            comm[2] = repeat;
        }

        comm[0] = commC;
        comm[1] = value;

        return comm;
    }
    private static void start() throws InterruptedException {
        do{
            Logger.getSingleton().println("<enter> para iniciar teclado de autenticação");
            in.nextLine();
            Logger.getSingleton().println("Verifique o teclado...");

        }while(!pit.setup());

        do{

            int[] command = commandMenu();
            int c = command[0],
                v = command[1],
                repeat = command[2];

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
                case 'd':
                case 's':
                case 'w':
                    if(v == -1)
                        v = 10;
                    break;
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
                case 'p':
                    pit.getPower();
                    break;
                case 't':
                    long startTimestamp = new Date().getTime();
                    pit.repeat(startTimestamp, repeat, v);
            }

        }while(true);
    }
    private static int[] commandMenu(){
        Logger.getSingleton().println("Comandos ( em minúsculo )",
                " N - Ligar\n" +
                        " M - Desligar\n\n" +
                        " P  - Visualizar Potência\n" +
                        " Q  <num (500pwr)>  - Reduzir Potência\n" +
                        " E  <num (1000pwr)> - Aumentar Potência\n" +
                        " W  <num (10deg)>   - Navegação\n" +
                        "ASD\n" +
                        " T  <comando> <repetições> - Avaliação"

        );
        return getCommand();
    }

}
