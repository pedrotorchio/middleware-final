package controllers.cockpit.components;

import controllers.simulators.Airplane;
import middleAir.common.exceptions.MiddleAirException;
import middleAir.common.remoteservice.InstanceService;
import middleAir.common.requesthandler.Request;

public class Yoke extends InstanceService implements IYoke{
    Airplane plane;

    public Yoke(Airplane plane){
        this.plane = plane;
        uid = "air-yoke";
    }

    public int steer(int degrees){
        // angulo positivo, sentido horário
        System.out.println("Steering to " + degrees + "deg" + " " + (degrees > 0 ? "CW" : "CCW"));

        rotateFlap(degrees/2, -degrees/2, (left,right) -> {
            setIntermediateValue(""+(plane.fl.getAngle() - plane.fr.getAngle()));
            return true;
        });

        return plane.fl.getAngle() - plane.fr.getAngle();

    }
    public int rise(int degrees){
        // angulo positivo, bico para cima

        System.out.println("Rising to " + degrees + "deg"+ " " + (degrees > 0 ? "Up" : "Down"));

        rotateFlap(degrees/2, degrees/2, (left,right) -> {
            setIntermediateValue(""+(plane.fl.getAngle() + plane.fr.getAngle()));
            return left==right;
        });

        return plane.fl.getAngle() + plane.fr.getAngle();

    }
    private interface Reached{
        boolean check(int left, int right);
    }
    public void rotateFlap(final int leftRotation, final int rightRotation){
        rotateFlap(leftRotation, rightRotation, (left,right)->true);
    }
    public void rotateFlap(final int leftGoal, final int rightGoal, Reached extrachecker){

        int i = 0,
            leftRotation = 0,
            rightRotation = 0;

        // enquanto os criterios de erro nao forem atendidos (erro < 10%) ou tentativas ainda não ultrapassarem 10
        System.out.print("Rotating Flaps (" + leftGoal + ", " + rightGoal+ ") = ");
        do {


                leftRotation += plane.fl.rotate(leftGoal - leftRotation);
                rightRotation += plane.fr.rotate(rightGoal - rightRotation);



            System.out.print("(" + leftRotation + ", " + rightRotation + ").. ");

            i++;

        }while(
                // quando criterios forem atendidos OU contagem ultrapassar, pare de tentar
                // tentar enquanto criterios NAO forem atendidos E contagem for pequena
                !( Math.abs(leftGoal - leftRotation)   <= Math.abs(.2*leftGoal) &&
                   Math.abs(rightGoal - rightRotation) <= Math.abs(.2*rightGoal) &&
                    extrachecker.check(leftRotation, rightRotation)) &&
                        i < 12
        );

        System.out.println("= (" + plane.fl.getAngle() + ", " + plane.fr.getAngle() + ")");


    }

    public String call(Request req, String methodname, String[] parameters) throws MiddleAirException{


        String angle = parameters[0];

        switch (methodname) {
            case "steer":
                return "" + steer(Integer.parseInt(angle));

            case "rise":
                return "" + rise(Integer.parseInt(angle));

            default:
                return "DEFAULT";
        }

    }

    public boolean isProtected() {
        return true;
    }
}
