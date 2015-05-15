package ch.bono88.tariffe;

import ch.bono88.storico.Chiamata;
import ch.bono88.supsicom.Tariffe;
import ch.bono88.supsicom.TelefonoBase;
import ch.bono88.supsicom.Utente;


public class TopFriend extends Tariffe {
    private Utente[] amici;

    private static final float PRICE_CALL_FRIEND = 0.05f;
    private static final float PRICE_CALL = 0.15f;

    public TopFriend() {
        this.amici = new Utente[5];
    }

    public float getCallCost(Chiamata c) {
        for (int i = 0; i < amici.length; i++)
            if (amici[i] != null)
                for (TelefonoBase t : amici[i].getTelefoni())
                    if (t.getSim().getNumeroTelefono().equals(c.getNumero()))
                        return c.getDurata() * PRICE_CALL_FRIEND;
        return c.getDurata() * PRICE_CALL;
    }

    public void addAmico(Utente u) throws Exception {
        for (int i = 0; i < amici.length; i++)
            if (amici[i] == null) {
                amici[i] = u;
                return;
            }
        throw new Exception("Raggiunto limite massimo di 5 amici!");
    }
}
