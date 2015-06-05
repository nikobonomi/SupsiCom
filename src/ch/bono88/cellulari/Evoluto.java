package ch.bono88.cellulari;

import java.io.Serializable;
import java.util.ArrayList;

import ch.bono88.exceptions.OutOfMaxConnectionsException;
import ch.bono88.storico.Chiamata;
import ch.bono88.storico.SMS;
import ch.bono88.supsicom.Cella;
import ch.bono88.supsicom.Sim; 
import ch.bono88.supsicom.TelefonoBase;

public class Evoluto extends TelefonoBase implements Serializable{


    protected ArrayList<Chiamata> avvisiChiamate;
    private static final long serialVersionUID = 2L;

    private boolean               isSegOn;


    public Evoluto() {
        super();
        this.avvisiChiamate = new ArrayList<>();
    }

    public void incCall(String numero, int durata) {
        //implemento il sistema di avviso chiamata in caso che il telefono è spento
        if (isOn())
            addToRegCall(numero, 0, true);
        else
            addToAvvisoCall(numero);
    }

    public void turnOn(Cella c) throws OutOfMaxConnectionsException {
        this.isOn = true;
        //se ci sono sms in attesa di ricezione li "ricevo"
        if (avvisoSMS.size() > 0) {
            for (SMS s : avvisoSMS)
                addToRegSMS(s);
            avvisoSMS.clear();
        }

        //se sono state ricevute chiamate quando si era spente le aggiungo al registro.
        if (avvisiChiamate.size() > 0) {
            for (Chiamata call : avvisiChiamate)
                addToRegCall(call);
            avvisiChiamate.clear();
        }

        // mi connetto alla cella
        connectCella(c);
    }

    public void turnOffSeg() {
        this.isSegOn = false;
    }

    public boolean isOnSeg() {
        return this.isSegOn;
    }

    public void addToAvvisoCall(String numero) {
        this.avvisiChiamate.add(new Chiamata(numero, 0, false, true));
    }
}
