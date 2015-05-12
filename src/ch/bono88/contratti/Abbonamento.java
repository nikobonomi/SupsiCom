package ch.bono88.contratti;

import ch.bono88.supsicom.Contratto;
import ch.bono88.supsicom.Sim;
import ch.bono88.supsicom.Utente;

public class Abbonamento extends Contratto{
   public Abbonamento(Utente firmatario, Sim s, int tariffaType) throws Exception{
        super(firmatario, s, tariffaType);
   }
}
