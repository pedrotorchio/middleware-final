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

        // viaja um pouco
        Thread.sleep(3000);

        on();
        powerUp(3000);
        steer(20);
        steer(-20);
        powerDown(2500);
        rise(-30);

    }
    public int rise(int angle){
        printTitle("Subindo/Descendo " + angle + " graus");
        angle = yoke.rise(angle);
        printResult("Ângulo Final:" + angle);

        return angle;
    }
    public int steer(int angle){
        printTitle("Virando " + angle + " graus");
        angle = yoke.rise(angle);
        printResult("Ângulo Final:" + angle);

        return angle;
    }
    public boolean off(){
        printTitle("Desligando motor");
        boolean off = throttle.off();
        if(off)
            printResult("Motor Desligado");
        else
            printResult("Não foi possível desligar todos os motores");

        return off;
    }
    public boolean on(){
        printTitle("Ligando motor");
        boolean on = throttle.on();
        if(on)
            printResult("Motor Ligado");
        else
            printResult("Não foi possível ligar todos os motores");
        return on;
    }
    public int powerUp(int power){
        printTitle("Acelerando " + power + "pwr");
        power = throttle.powerUp(power);
        printResult("Potência Final:" + power);
        return power;
    }
    public int powerDown(int power){
        printTitle("Desacelerando " + power + "pwr");
        power = throttle.powerDown(power);
        printResult("Potência Final:" + power);

        return power;
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
