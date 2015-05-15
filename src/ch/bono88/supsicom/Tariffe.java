package ch.bono88.supsicom;

public abstract class Tariffe {
    //Tipologia di tariffa selezionata
    public static final int TIPO_TAR_BASE = 0;
    public static final int TIPO_TAR_CNIG = 1;
    public static final int TIPO_TAR_TFRI = 2;

    public static final float PRICE_SMS= 0.05f;
    public static final float PRICE_MMS= 0.10f;
    public static final float PRICE_VIDEOCALL= 0.20f;

    public Tariffe() {

    }
}
