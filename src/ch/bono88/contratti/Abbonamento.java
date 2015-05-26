package ch.bono88.contratti;

import ch.bono88.storico.Chiamata;
import ch.bono88.supsicom.*;
import ch.bono88.tariffe.Base;
import ch.bono88.tariffe.CallNight;
import ch.bono88.tariffe.TopFriend;

import java.util.ArrayList;
import java.util.List;

public class Abbonamento extends Contratto{
    private List<Attivita> costi;

   public Abbonamento(SupsiCom master, Utente firmatario, Sim s, int tariffaType) throws Exception{
        super(master ,firmatario, s, tariffaType);
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
        costi.add(a);
    }

    public void accreditaSMS(String from){
        //todo fare controllo 2 anni per tariffa fedelt`a
        Attivita a = new Attivita();
        a.setCost(tariffa.PRICE_SMS);
        a.setFrom(from);
        a.setType(Attivita.TYPE_SMS);
        costi.add(a);
    }

    public void accreditaMMS(String from){
        //todo fare controllo 2 anni per tariffa fedelt`a
        Attivita a = new Attivita();
        a.setCost(tariffa.PRICE_MMS);
        a.setFrom(from);
        a.setType(Attivita.TYPE_MMS);
        costi.add(a);
    }

    //genero la fattura secondo le attività solte fin'ora
    public Fattura generateFatura(){
        Fattura f = new Fattura(new ArrayList<>(costi));
        costi.clear();
        return f;
    }

    public void printAttivita(){
        float totalExpense = 0;
        for(Attivita a : costi){
            totalExpense += a.getCost();
            System.out.println(a.getCost());
        }

        System.out.println("Spese totali " + totalExpense);
    }

    private void accreditaVideoCall(Chiamata c){
        Attivita a = new Attivita();
        a.setCost(tariffa.PRICE_VIDEOCALL *c.getDurata());
        a.setFrom(c.getNumero());
        a.setType(Attivita.TYPE_MMS);
    }
}
