package ch.bono88.supsicom;

import java.util.List;

/**
 * Created by Niko on 26.05.2015.
 */
public class Fattura {
    private List<Attivita> attivita;
    private float totalCost;

    public Fattura(List<Attivita> attivita){
        totalCost = 0;
        this.attivita = attivita;
        for(Attivita a : attivita)
            totalCost += a.getCost();
    }

    public List<Attivita> getAttivita(){
        return this.attivita;
    }

    public float getTotalCost(){
        return this.totalCost;
    }
}
