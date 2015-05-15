package ch.bono88.contratti;

import ch.bono88.supsicom.Contratto;
import ch.bono88.supsicom.Sim;
import ch.bono88.supsicom.Utente;

public class Prepagato extends Contratto {
    private float saldo;

    public Prepagato(Utente firmatario, Sim s, int tariffaType) throws Exception {
        super(firmatario, s, tariffaType);
        this.saldo = 10;
    }

    public float getSaldo() {
        return saldo;
    }

    public void setSaldo(float saldo) {
        this.saldo = saldo;
    }

    public void accredita(int durata){
        //todo accredita una chiamata
    }
}
