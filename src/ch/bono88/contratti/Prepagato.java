package ch.bono88.contratti;

import ch.bono88.storico.Chiamata;
import ch.bono88.supsicom.Contratto;
import ch.bono88.supsicom.Sim;
import ch.bono88.supsicom.SupsiCom;
import ch.bono88.supsicom.Utente;
import ch.bono88.tariffe.Base;
import ch.bono88.tariffe.CallNight;
import ch.bono88.tariffe.TopFriend;


public class Prepagato extends Contratto {
    private float saldo;

    public Prepagato(SupsiCom supsiCom, Utente firmatario, Sim s, int tariffaType) throws Exception {
        super(supsiCom,firmatario, s, tariffaType);
        //Supsicom condona 10 franchi di ricarica iniziale
        this.saldo = 2;
    }

    public float getSaldo() {
        return saldo;
    }

    public void setSaldo(float saldo) {
        this.saldo = saldo;
    }

    public void checkSaldo() throws Exception{
        if(saldo<2)
            master.sendSUPSICOMSMS(getSim().getNumeroTelefono(),"Attenzione! credito inferiore a 2 chf! Le restano " + saldo + " chf");

    }

    public void accreditaChiamata(Chiamata c) throws Exception{
        if (tariffa instanceof Base)
            saldo -= Base.PRICE_CALL_START + Base.PRICE_CALL * c.getDurata();
        else if (tariffa instanceof TopFriend)
            saldo -= ((TopFriend) tariffa).getCallCost(c);
        else if(tariffa instanceof CallNight)
            saldo -= ((CallNight) tariffa).getCallCost(c);
        else throw new Exception("Nessuna tariffa trovata!");

        checkSaldo();
    }

    public void accreditaSMS() throws Exception{
        //todo fare controllo 2 anni per tariffa fedelt`a
        saldo -= tariffa.PRICE_SMS;

        checkSaldo();
    }

    public void accreditaMMS() throws Exception{
        //todo fare controllo 2 anni per tariffa fedelt`a
        saldo -= tariffa.PRICE_MMS;

        checkSaldo();
    }

    private void accreditaVideoCall(Chiamata c){
        saldo -= tariffa.PRICE_VIDEOCALL *c.getDurata();
    }
}
