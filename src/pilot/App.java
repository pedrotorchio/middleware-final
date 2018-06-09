package pilot;

import java.util.Scanner;

public class App {

    public static void main(String[] args) throws InterruptedException {

        enter2start();
        new Cockpit().emulate();
    }

    private static void enter2start(){
        System.out.println("Pressione <enter> para inicializar cockpit.");
        Scanner in = new Scanner(System.in);
                in.nextLine();
    }

//
//    public static void fakeUse(){
//        for(int i = 0 ; i < 4 ; i++){
//            yoke.rise(i*10);
//            yoke.steer(-i*10);
//        }
//    }

}
