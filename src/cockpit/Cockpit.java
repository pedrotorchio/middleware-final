package cockpit;

import controllers.cockpit.proxies.AuthenticationKeyboardProxy;
import controllers.cockpit.proxies.MonitorProxy;
import controllers.cockpit.proxies.ThrottleProxy;
import controllers.cockpit.proxies.YokeProxy;
import middleAir.MiddleAir;
import middleAir.common.exceptions.NotFoundException;

public class Cockpit {
    MiddleAir mAir = null;
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
        if(mAir == null) return 0;
        printTitle("Subindo/Descendo " + angle + " graus");
        angle = yoke.rise(angle);
        printResult("Ângulo Final:" + angle);

        return angle;
    }
    public int steer(int angle){
        if(mAir == null) return 0;
        printTitle("Virando " + angle + " graus");
        angle = yoke.steer(angle);
        printResult("Ângulo Final:" + angle);

        return angle;
    }
    public boolean off(){
        if(mAir == null) return false;
        printTitle("Desligando motor");
        boolean off = throttle.off();
        if(off)
            printResult("Motor Desligado");
        else
            printResult("Não foi possível desligar todos os motores");

        return off;
    }
    public boolean on(){
        if(mAir == null) return false;
        printTitle("Ligando motor");
        boolean on = throttle.on();
        if(on)
            printResult("Motor Ligado");
        else
            printResult("Não foi possível ligar todos os motores");
        return on;
    }
    public int powerUp(int power){
        if(mAir == null) return 0;
        printTitle("Acelerando " + power + "pwr");
        power = throttle.powerUp(power);
        printResult("Potência Final:" + power);
        return power;
    }
    public int powerDown(int power){
        if(mAir == null) return 0;
        printTitle("Desacelerando " + power + "pwr");
        power = throttle.powerDown(power);
        printResult("Potência Final:" + power);

        return power;
    }
    public int getPower(){
        if(mAir == null) return 0;
        int power = throttle.getPower();
        printTitle("Potência " + power + "pwr");

        return power;
    }
    public boolean isOn(){
        if(mAir == null) return false;
        boolean ison = throttle.isOn();

        printTitle("Motores " + (ison ? "ligados" : "desligados"));

        return ison;
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

        mAir = new MiddleAir();
        AuthenticationKeyboardProxy authKeyboard;

        try {

            authKeyboard = new AuthenticationKeyboardProxy(mAir.lookup("auth-keyboard"));
            if(!mAir.authenticate(authKeyboard))
                return false;

            yoke         = new YokeProxy(mAir.lookup("air-yoke"));
            throttle     = new ThrottleProxy(mAir.lookup("air-throttle"));


        } catch (NotFoundException e) {
            monitor.writeError("Problemas encontrados.");
            return false;
        }

        return mAir.checkComponents();
    }

}
