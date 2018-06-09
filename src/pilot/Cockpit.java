package pilot;

import controllers.cockpit.proxies.AuthenticationKeyboardProxy;
import controllers.cockpit.proxies.MonitorProxy;
import controllers.cockpit.proxies.ThrottleProxy;
import controllers.cockpit.proxies.YokeProxy;
import middleAir.MiddleAir;
import middleAir.common.exceptions.NotFoundException;

public class Cockpit {
    MiddleAir mAir = new MiddleAir();
    YokeProxy yoke;
    ThrottleProxy throttle;
    MonitorProxy monitor;

    public void emulate() throws InterruptedException {
        while(!setup());


        printTitle("INICIANDO SIMULAÇÃO");


        on();
        powerUp(3000);
        rise(45);
        rise(-45);
        off();

        Thread.sleep(3000);

        on();
        powerUp(3000);
        steer(20);
        steer(-20);
        powerDown(2500);
        rise(-30);

    }
    public void rise(int angle){
        printTitle("Subindo/Descendo " + angle + " graus");
        printResult("Ângulo Final:" + yoke.rise(angle));
    }
    public void steer(int angle){
        printTitle("Virando " + angle + " graus");
        printResult("Ângulo Final:" + yoke.rise(angle));
    }
    public void off(){
        printTitle("Desligando motor");
        if(throttle.off())
            printResult("Motor Desligado");
    }
    public void on(){
        printTitle("Ligando motor");
        if(throttle.on())
            printResult("Motor Ligado");
    }
    public void powerUp(int power){
        printTitle("Acelerando " + power + "pwr");
        printResult("Potência Final:" + throttle.powerUp(power));
    }
    public void powerDown(int power){
        printTitle("Desacelerando " + power + "pwr");
        printResult("Potência Final:" + throttle.powerDown(power));
    }

    public void printTitle(String title){
        System.out.println("\n--------[ " + title);
    }
    public void printResult(String message){
        message = message == null? "" : "\t\t" + message + "\n";
        System.out.println(message);
    }
    public boolean setup(){
        // autentica usuário com nome e senha
        // define o canal de saída para mensagens dos proxies (monitor do cockpit)

        AuthenticationKeyboardProxy authKeyboard;

        try {

            monitor      = new MonitorProxy(mAir.lookup("output-monitor"));
            mAir.setOutputChannel(monitor);

            authKeyboard = new AuthenticationKeyboardProxy(mAir.lookup("auth-keyboard"));
            mAir.waitAuthentication(authKeyboard);

            yoke         = new YokeProxy(mAir.lookup("air-yoke"));
            throttle     = new ThrottleProxy(mAir.lookup("air-throttle"));


        } catch (NotFoundException e) {

            return false;
        }

        return mAir.checkComponents();
    }

}
