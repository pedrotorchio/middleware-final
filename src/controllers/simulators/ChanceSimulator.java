package controllers.simulators;

import controllers.exceptions.ComponentException;

public class ChanceSimulator {
public static void main(String[] args){
    ChanceSimulator chance = new ChanceSimulator();
    int centroid = 100;
    for(int var = 10 ; var < 100 ; var += 10)
        for(int i = 0 ; i < 10 ; i++){
            double sort = chance.range(centroid, var);

            double desv = (1 - sort/centroid)*100;
            System.out.println(centroid + ", " + var + " = " + sort + " (" + desv + "%)");
        }

}
    public double random(){
        return Math.random();
    }
    public boolean toss(final int PROB){
        if(PROB == 0)
            return false;

        double rand = random();
        // numero entre 0 e 100
        // modulo
        // 100 / RISK ~ 5% -> 100/5 = 20
        // se numero sorteado for múltiplo de 100/RISK (~20), haverá falha
        return (int)(rand*(100+1)) % 100/PROB == 0;
    }

    public double range(final double centroid, final int variancia){

        // variancia/2 para mais e para menos
        double range = centroid * ((double)variancia)/100;
        return centroid + (range*random() - range/2);
    }

    public double rangeWithFailure(final double centroid, final int variancia, final int prob, final int fail_amp)
            throws ComponentException {

        double value = range(centroid, variancia);



        if(toss(prob)){
            value = range(value, fail_amp);
            throw new ComponentException("Falha", value);
        }

        return value;
    }
}
