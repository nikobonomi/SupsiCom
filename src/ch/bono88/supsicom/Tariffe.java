package ch.bono88.supsicom;

import java.io.Serializable;

public abstract class Tariffe implements Serializable{
    //Tipologia di tariffa selezionata
    public static final int TIPO_TAR_BASE = 0;
    public static final int TIPO_TAR_CNIG = 1;
    public static final int TIPO_TAR_TFRI = 2;
    private static final long serialVersionUID = 13L;

    public static final float PRICE_SMS = 0.05f;
    public static final float PRICE_MMS = 0.10f;
    public static final float PRICE_SMS_FEDELI = 0f;
    public static final float PRICE_MMS_FEDELI = 0.05f;
    public static final float PRICE_VIDEOCALL = 0.20f;

    public Tariffe() {

    }
}
