package ch.bono88.contratti;

import ch.bono88.exceptions.NumberNotFoundException;
import ch.bono88.exceptions.TariffaNotFoundException;
import ch.bono88.storico.Chiamata;
import ch.bono88.supsicom.Contratto;
import ch.bono88.supsicom.Sim;
import ch.bono88.supsicom.SupsiCom;
import ch.bono88.supsicom.Utente;
import ch.bono88.tariffe.Base;
import ch.bono88.tariffe.CallNight;
import ch.bono88.tariffe.TopFriend;

import java.io.Serializable;

public class Prepagato extends Contratto implements Serializable{
    private float saldo;
    private static final long serialVersionUID = 4L;

    public static final int RIC_100 = 100;
    public static final int RIC_50 = 50;
    public static final int RIC_10 = 10;

    public Prepagato(SupsiCom supsiCom, Utente firmatario, Sim s, int tariffaType) throws TariffaNotFoundException {
        super(supsiCom,firmatario, s, tariffaType);
        //Supsicom condona 20 franchi di ricarica iniziale
        this.saldo = 20;
    }

    public float getSaldo() {
        return saldo;
    }

    public void setSaldo(float saldo) {
        this.saldo = saldo;
    }

    public void checkSaldo() throws NumberNotFoundException {
        if(saldo<2)
            master.sendSUPSICOMSMS(getSim().getNumeroTelefono(),"Attenzione! credito inferiore a 2 chf! Le restano " + saldo + " chf");
    }

    public void ricarica(int saldo){
        this.saldo += saldo;
    }

    public void accreditaChiamata(Chiamata c) throws TariffaNotFoundException, NumberNotFoundException {
        if (tariffa instanceof Base)
            saldo -= Base.PRICE_CALL_START + Base.PRICE_CALL * c.getDurata();
        else if (tariffa instanceof TopFriend)
            saldo -= ((TopFriend) tariffa).getCallCost(c);
        else if(tariffa instanceof CallNight)
            saldo -= ((CallNight) tariffa).getCallCost(c);
        else throw new TariffaNotFoundException("Nessuna tariffa trovata!");

        checkSaldo();
    }

    public void accreditaSMS() throws NumberNotFoundException {
        //todo fare controllo 2 anni per tariffa fedelt`a
        saldo -= tariffa.PRICE_SMS;

        checkSaldo();
    }

    public void accreditaMMS() throws NumberNotFoundException {
        //todo fare controllo 2 anni per tariffa fedelt`a
        saldo -= tariffa.PRICE_MMS;

        checkSaldo();
    }

    public void accreditaVideoCall(Chiamata c){
        saldo -= tariffa.PRICE_VIDEOCALL *c.getDurata();
    }
}
