package ch.bono88.storico;

import java.io.Serializable;
import java.util.Date;


public class Chiamata implements Serializable{
    private static final long serialVersionUID = 4L;
    private String numero;
    private Date data;
    private int durata;
    private boolean isVideocall;
    private boolean isIncoming;

    public Chiamata(String numero, int durata, boolean isVideocall, boolean isIncoming) {
        this.numero = numero;
        this.data = new Date();
        this.durata = durata;
        this.isVideocall = isVideocall;
        this.isIncoming = isIncoming;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        if (isIncoming)
            sb.append("DA:");
        else sb.append("A:");

        sb
                .append(numero)
                .append(" Durata: ")
                .append(durata)
                .append(" Data")
                .append(data)
                .toString();

        return sb.toString();

    }

    public String getNumero() {
        return this.numero;
    }

    public int getDurata() {
        return this.durata;
    }

    public Date getData() {
        return this.data;
    }

    public boolean isVideoCall() {
        return this.isVideocall;
    }


}
