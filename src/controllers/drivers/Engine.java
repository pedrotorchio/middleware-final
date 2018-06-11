package controllers.drivers;

public class Engine extends AirplaneComponentDriver  {

    protected final int LAXITY = 50; // %
    protected int power = 0;
    protected boolean isOn = false;

    public static void main(String [] args){
        Engine e = new Engine();

        for(int i = -10; i < 10 ; i++){
            int power = i * 200;
            System.out.println(e.getPower() + " " + power + " " + e.power(power));
        }
    }
    public boolean isOn(){
        return isOn;
    }
    public int power(int horses) {


        int throttle = (int) work(horses);
            throttle = setPower(power + throttle);


        return throttle;
    }

    private int setPower(int power){
        // LIMITA VALOR E RETORNA VARIAÇÃO COM VALOR ANTIGO

        if(!isOn())
            return 0;

        power = power > 1500 ? 1500 : power < -1000 ? -1000 : power;

        int powered = power - this.power;

        // cada 1000pwr leva 10s
        sleep(Math.abs(powered*10));

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
