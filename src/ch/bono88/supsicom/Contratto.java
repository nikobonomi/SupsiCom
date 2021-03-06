package ch.bono88.supsicom;

import ch.bono88.exceptions.TariffaNotFoundException;
import ch.bono88.tariffe.Base;
import ch.bono88.tariffe.CallNight;
import ch.bono88.tariffe.TopFriend;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public abstract class Contratto implements Serializable{
    private Utente uFirmatario;
    private Sim sim;
    protected Tariffe tariffa;
    private boolean hasSegreteriaAbilitata;
    protected Date dataCreazione;
    protected SupsiCom master;
    private static final long serialVersionUID = 8L;

    //tipologia che si utilizza nella creazione epr sapere
    //se si vuole create un'abbonamento o una prepagata
    public static final int TIPO_ABB = 0;
    public static final int TIPO_PRE = 1;


    public Contratto(SupsiCom supsicom, Utente uFirmatario, Sim s, int tariffaTipo) throws TariffaNotFoundException {
        this.master = supsicom;
        this.dataCreazione = new Date();
        this.uFirmatario = uFirmatario;
        this.sim = s;
        this.hasSegreteriaAbilitata = false;
        if (tariffaTipo == Tariffe.TIPO_TAR_BASE)
            this.tariffa = new Base();
        else if (tariffaTipo == Tariffe.TIPO_TAR_CNIG)
            this.tariffa = new CallNight();
        else if (tariffaTipo == Tariffe.TIPO_TAR_TFRI)
            this.tariffa = new TopFriend();
        else
            throw new TariffaNotFoundException("Il tipo di tariffa selezionato non esiste");


    }

    public Sim getSim() {
        return this.sim;
    }

    public Utente getFirmatario() {
        return this.uFirmatario;
    }

    public Tariffe getTariffa() {
        return this.tariffa;
    }

    public void setTariffa(Tariffe tariffa) {
        this.tariffa = tariffa;
    }

    public boolean isHasSegreteriaAbilitata() {
        return hasSegreteriaAbilitata;
    }

    public void setHasSegreteriaAbilitata(boolean hasSegreteriaAbilitata) {
        this.hasSegreteriaAbilitata = hasSegreteriaAbilitata;
    }
}
