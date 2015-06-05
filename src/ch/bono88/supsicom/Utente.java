package ch.bono88.supsicom;

import ch.bono88.storico.Chiamata;
import ch.bono88.storico.SMS;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class Utente implements Serializable {
    private double                  dAVS;
    private String                  strNome;
    private String                  strCognome;
    private int                     intCAP;

    private static final long serialVersionUID = 1L;

    private transient List<TelefonoBase> telefoni;


    public Utente(String strNome, String strCognome, int intCAP, double dAVS) {
        this.strNome = strNome;
        this.strCognome = strCognome;
        this.intCAP = intCAP;
        this.dAVS = dAVS;
        this.telefoni = new ArrayList<>();
    }

    public void printReg() {
        //Stampo tutte le chiamate
        System.out.println("Chiamate");
        for (TelefonoBase t : telefoni)
            for (Chiamata c : t.getRegChiamate())
                if (!c.isVideoCall())
                    System.out.println(c);

        //Stampo tutte le videochiamate
        System.out.println("Video Chiamate");
        for (TelefonoBase t : telefoni)
            for (Chiamata c : t.getRegChiamate())
                if (c.isVideoCall())
                    System.out.println(c);


        //Stampo tutte i i messaggi
        System.out.println("SMS");
        for (TelefonoBase t : telefoni)
            for (SMS s : t.getRegSMS())
                if (!s.isMMS())
                    System.out.println(s);

        //Stampo tutte i i messaggi
        System.out.println("MMS");
        for (TelefonoBase t : telefoni)
            for (SMS s : t.getRegSMS())
                if (s.isMMS())
                    System.out.println(s);


    }

    public String getNome() {
        return this.strNome;
    }

    public List<TelefonoBase> getTelefoni() {
        return telefoni;
    }

    public void addTelefono(TelefonoBase tel) {
        if (telefoni == null)
            telefoni = new ArrayList<>();
        telefoni.add(tel);

    }

    public String getCognome() {
        return this.strCognome;
    }

    public double getAVS() {
        return this.dAVS;
    }

}
