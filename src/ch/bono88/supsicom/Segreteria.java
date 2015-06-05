package ch.bono88.supsicom;


import java.io.Serializable;
import java.util.Date;

public class Segreteria implements Serializable{
    private static final long serialVersionUID = 10L;
    private Date date;
    private String message;
    private String numMittente;
    private Sim ricevente;
    private int durata;

    public Segreteria( String message, String numMittente, Sim ricevente, int durata) {
        this.date = new Date();
        this.message = message;
        this.numMittente = numMittente;
        this.ricevente = ricevente;
        this.durata = durata;
    }

    public int getDurata() {
        return durata;
    }

    public Sim getRicevente() {
        return ricevente;
    }

    public Date getDate() {
        return date;
    }

    public String getMessage() {
        return message;
    }

    public String getNumMittente() {
        return numMittente;
    }
}
