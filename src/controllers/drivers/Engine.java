package controllers.drivers;

public class Engine extends AirplaneComponentDriver {

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
    public int power(int horses) {


        int throttle = (int) work(horses);
            throttle = setPower(power + throttle);


        return throttle;
    }

    private int setPower(int power){
        // LIMITA VALOR E RETORNA VARIAÇÃO COM VALOR ANTIGO

        if(!isOn)
            return 0;

        power = power > 5000 ? 5000 : power < -2000 ? -2000 : power;

        int powered = power - this.power;
        this.power = power;

        return powered;

    }

    public void off(){

        setPower(0);
        isOn = false;

    }
    public void on(){
        setPower(0);
        isOn = true;
    }

    protected int getLaxity(){
        return LAXITY;
    }

    public int getPower(){
        return power;
    }

}
