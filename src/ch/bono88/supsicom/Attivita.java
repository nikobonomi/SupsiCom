package ch.bono88.supsicom;

import java.util.Date;

/**
 * Created by Niko on 22.05.2015.
 */
public class Attivita {
    private int type;
    private float cost;
    private String from;
    private Date data;
    private int durata;

    public static final int TYPE_CALL =1;
    public static final int TYPE_SMS=2;
    public static final int TYPE_MMS=3;
    public static final int TYPE_VIDEO=3;

    public Attivita(int type, float cost, String from) {
        this.type = type;
        this.cost = cost;
        this.from = from;
        this.data = new Date();
    }

    public Attivita(int type, float cost, String from,int durata) {
        this.type = type;
        this.cost = cost;
        this.from = from;
        this.durata = durata;
        this.data = new Date();
    }

    public Attivita(){
        this.data = new Date();}

    public int getDurata() {
        return durata;
    }

    public void setDurata(int durata) {
        this.durata = durata;
    }

    public Date getData() {
        return data;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public float getCost() {
        return cost;
    }

    public void setCost(float cost) {
        this.cost = cost;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }
}
