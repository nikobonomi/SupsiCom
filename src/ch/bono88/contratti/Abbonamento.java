package ch.bono88.contratti;

import ch.bono88.storico.Chiamata;
import ch.bono88.supsicom.Attivita;
import ch.bono88.supsicom.Contratto;
import ch.bono88.supsicom.Sim;
import ch.bono88.supsicom.Utente;
import ch.bono88.tariffe.Base;
import ch.bono88.tariffe.CallNight;
import ch.bono88.tariffe.TopFriend;

import java.util.ArrayList;
import java.util.List;

public class Abbonamento extends Contratto{
    private List<Attivita> costi;

   public Abbonamento(Utente firmatario, Sim s, int tariffaType) throws Exception{
        super(firmatario, s, tariffaType);
        costi = new ArrayList<>();
   }

    public void accreditaChiamata(Chiamata c) throws Exception{
        Attivita a = new Attivita();
        if (tariffa instanceof Base)
            a.setCost(Base.PRICE_CALL_START + Base.PRICE_CALL * c.getDurata());
        else if (tariffa instanceof TopFriend)
            a.setCost(((TopFriend) tariffa).getCallCost(c));
        else if(tariffa instanceof CallNight)
            a.setCost(((CallNight) tariffa).getCallCost(c));
        else throw new Exception("Nessuna tariffa trovata!");
        a.setType(Attivita.TYPE_CALL);
        a.setFrom(c.getNumero());
    }
}
