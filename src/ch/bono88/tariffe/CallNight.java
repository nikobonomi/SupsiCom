package ch.bono88.tariffe;

import ch.bono88.storico.Chiamata;
import ch.bono88.supsicom.Tariffe;
import ch.bono88.supsicom.TelefonoBase;


public class CallNight extends Tariffe {

    private static final float PRICE_CALL_NIGHT = 0.05f;
    private static final float PRICE_CALL = 0.15f;

    public CallNight() {
    }

    public float getCallCost(Chiamata c) {
        if (c.getData().getHours() > 18 && c.getData().getHours() < 8)
            return c.getDurata() * PRICE_CALL_NIGHT;
        return c.getDurata() * PRICE_CALL;
    }
}
