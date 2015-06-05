package ch.bono88.tariffe;

import ch.bono88.supsicom.Tariffe;

import java.io.Serializable;

public class Base extends Tariffe implements Serializable{
    private static final long serialVersionUID = 15L;

    public static final float PRICE_CALL= 0.05f;
    public static final float PRICE_CALL_START = 0.15f;

    public Base() {

    }


}
