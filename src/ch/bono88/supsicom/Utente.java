package ch.bono88.supsicom;

import ch.bono88.storico.Chiamata;
import ch.bono88.storico.SMS;

import java.util.ArrayList;


public class Utente {
    private double                  dAVS;
    private String                  strNome;
    private String                  strCognome;
    private int                     intCAP;

    private ArrayList<TelefonoBase> telefoni;


    public Utente(String strNome, String strCognome, int intCAP, double dAVS) {
        this.strNome = strNome;
        this.strCognome = strCognome;
        this.intCAP = intCAP;
        this.dAVS = dAVS;
        this.telefoni = new ArrayList<TelefonoBase>();
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

    public ArrayList<TelefonoBase> getTelefoni() {
        return telefoni;
    }

    public void addTelefono(TelefonoBase tel) {
        telefoni.add(tel);

    }

    public ArrayList<TelefonoBase> getTelefoniNoSim() {
        ArrayList<TelefonoBase> telefoniNoSim = new ArrayList<TelefonoBase>();
        for (TelefonoBase t : telefoni)
            if (t.getSim().equals(null))
                telefoniNoSim.add(t);
        return telefoniNoSim;
    }

    public String getCognome() {
        return this.strCognome;
    }

    public int getCAP() {
        return this.intCAP;
    }

    public double getAVS() {
        return this.dAVS;
    }

}
