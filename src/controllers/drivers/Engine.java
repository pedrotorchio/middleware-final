package controllers.drivers;

import middleAir.common.logger.Logger;

public class Engine extends AirplaneComponentDriver  {

    protected final int LAXITY = 30; // %
    protected int power = 0;
    protected boolean isOn = false;

    public static void main(String [] args){
        Engine e = new Engine();

        e.on();
        for(int i = -10; i < 10 ; i++){
            int power = i * 200;
            System.out.print(e.getPower() + " " + power);
            System.out.println(" " + e.power(power));
        }
    }
    public boolean isOn(){
        return isOn;
    }
    public int power(int horses) {

        int throttle = (int) work(horses);
        Logger.getSingleton().maybePrintln(horses + " " + throttle);
            throttle = setPower(power + throttle);


        return throttle;
    }

    private int setPower(int power){
        // LIMITA VALOR E RETORNA VARIAÇÃO COM VALOR ANTIGO

        if(!isOn())
            return 0;

        sleep(1000);

        power = power > 5000 ? 5000 : power < -2000 ? -2000 : power;

        int powered = power - this.power;

        // 500pwr/s
        int time = Math.abs(powered*2);
        sleep(time);

        this.power = power;

        return powered;

    }

    public boolean off(){

        if(!isOn())
            return true;

        setPower(0);
        isOn = false;


        return true;
    }
    public boolean on(){
        if(isOn())
            return true;

        setPower(0);
        isOn = true;

        return true;
    }

    protected int getLaxity(){
        return LAXITY;
    }

    public int getPower(){
        return power;
    }

}
