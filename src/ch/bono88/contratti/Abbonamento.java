package ch.bono88.contratti;

import ch.bono88.exceptions.TariffaNotFoundException;
import ch.bono88.storico.Chiamata;
import ch.bono88.supsicom.*;
import ch.bono88.tariffe.Base;
import ch.bono88.tariffe.CallNight;
import ch.bono88.tariffe.TopFriend;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Abbonamento extends Contratto implements Serializable{
    private List<Fattura> fatture;
    private static final long serialVersionUID = 3L;

    public Abbonamento(SupsiCom master, Utente firmatario, Sim s, int tariffaType) throws TariffaNotFoundException {
        super(master, firmatario, s, tariffaType);
        fatture = new ArrayList<>();
    }

    public void accreditaChiamata(Chiamata c) throws TariffaNotFoundException {
        Attivita a = new Attivita();
        if (tariffa instanceof Base)
            a.setCost(Base.PRICE_CALL_START + Base.PRICE_CALL * c.getDurata());
        else if (tariffa instanceof TopFriend)
            a.setCost(((TopFriend) tariffa).getCallCost(c));
        else if (tariffa instanceof CallNight)
            a.setCost(((CallNight) tariffa).getCallCost(c));
        else throw new TariffaNotFoundException("Nessuna tariffa trovata!");
        a.setType(Attivita.TYPE_CALL);
        a.setFrom(c.getNumero());
        a.setDurata(c.getDurata());
        addAttivitaToFatt(a);
    }

    public void accreditaSMS(String from) {
        //todo fare controllo 2 anni per tariffa fedelt`a
        Attivita a = new Attivita();
        a.setCost(tariffa.PRICE_SMS);
        a.setFrom(from);
        a.setType(Attivita.TYPE_SMS);
        addAttivitaToFatt(a);
    }

    public void accreditaMMS(String from) {
        //todo fare controllo 2 anni per tariffa fedelt`a
        Attivita a = new Attivita();
        a.setCost(tariffa.PRICE_MMS);
        a.setFrom(from);
        a.setType(Attivita.TYPE_MMS);
        addAttivitaToFatt(a);
    }

    private void addAttivitaToFatt(Attivita a) {
        for (Fattura f : fatture)
            if(f.attivitaIsFattura(a)){
                f.addAttivita(a);
                return;
            }

        //se arrivo fin qui vuol dire che non esiste una fattura per il mese corrente

        Fattura f = new Fattura(a,this);
        fatture.add(f);

    }

    public List<Fattura> getFatture(){
        return this.fatture;
    }

    public void accreditaVideoCall(Chiamata c) {
        Attivita a = new Attivita();
        a.setCost(tariffa.PRICE_VIDEOCALL * c.getDurata());
        a.setFrom(c.getNumero());
        a.setType(Attivita.TYPE_VIDEO);
        a.setDurata(c.getDurata());
        addAttivitaToFatt(a);
    }
}
