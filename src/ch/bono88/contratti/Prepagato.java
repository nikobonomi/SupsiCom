package ch.bono88.contratti;

import ch.bono88.storico.Chiamata;
import ch.bono88.supsicom.Contratto;
import ch.bono88.supsicom.Sim;
import ch.bono88.supsicom.Utente;
import ch.bono88.tariffe.Base;
import ch.bono88.tariffe.CallNight;
import ch.bono88.tariffe.TopFriend;

public class Prepagato extends Contratto {
    private float saldo;

    public Prepagato(Utente firmatario, Sim s, int tariffaType) throws Exception {
        super(firmatario, s, tariffaType);
        //Supsicom condona 10 franchi di ricarica iniziale
        this.saldo = 10;
    }

    public float getSaldo() {
        return saldo;
    }

    public void setSaldo(float saldo) {
        this.saldo = saldo;
    }

    public void accreditaChiamata(Chiamata c) throws Exception{
        if (tariffa instanceof Base)
            saldo -= Base.PRICE_CALL_START + Base.PRICE_CALL * c.getDurata();
        else if (tariffa instanceof TopFriend)
            saldo -= ((TopFriend) tariffa).getCallCost(c);
        else if(tariffa instanceof CallNight)
            saldo -= ((CallNight) tariffa).getCallCost(c);

        else throw new Exception("Nessuna tariffa trovata!");
    }
}
