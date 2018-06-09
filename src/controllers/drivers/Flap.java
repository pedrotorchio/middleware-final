package controllers.drivers;

public class Flap extends AirplaneComponentDriver {

    protected final int LAXITY = 50; // %

    protected int angle = 0;
    public static void main(String [] args){
        Flap f = new Flap();
        for(int rotation = 0 ; rotation < 90 ; rotation+=15){
            int actualRotated;

            System.out.print(
                    f.getAngle()+"deg rotated " + rotation + "deg -> ");


            actualRotated = f.rotate(rotation);

            System.out.println(f.getAngle() + " ("  + actualRotated + "deg)");

        }
        for(int rotation = 0 ; rotation > -90 ; rotation-=10){
            int actualRotated;

            System.out.print(
                    f.getAngle()+"deg rotated " + rotation + "deg -> ");


            actualRotated = f.rotate(rotation);



            System.out.println(f.getAngle() + " ("  + actualRotated + "deg)");

        }
    }
    public int rotate(int degrees){

        int rotation;
        // grau de rotação ilimitado
        rotation = (int) work(degrees);
        // quanto de fato poderá ser rotacionado
        rotation = setAngle(angle + rotation);

        return rotation;

    }

    protected int getLaxity(){
        return LAXITY;
    }

    private int setAngle(int angle){
        // LIMITA VALOR E RETORNA VARIAÇÃO COM VALOR ANTIGO
        angle = angle > 90 ? 90 : angle < -90 ? -90 : angle;

        int rotated = angle - this.angle;
        this.angle = angle;


        return rotated;

    }
    public int getAngle(){
        return angle;
    }
}
