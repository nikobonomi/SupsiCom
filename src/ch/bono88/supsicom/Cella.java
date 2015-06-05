package ch.bono88.supsicom;

import ch.bono88.exceptions.OutOfMaxConnectionsException;
import ch.bono88.utils.NumeroTelefono;

import java.awt.font.NumericShaper;
import java.io.Serializable;
import java.util.ArrayList;

public class Cella implements Serializable{

    private ArrayList<TelefonoBase> alTelConnessi;
    private SupsiCom                master;
    private int                     maxConnections;
    private static final long serialVersionUID = 7L;


    public Cella(SupsiCom master, int maxConnections) {
        this.master = master;
        this.maxConnections = maxConnections;
        this.alTelConnessi = new ArrayList<>();
    }

    public SupsiCom getMaster() {
        return this.master;
    }

    public boolean connPossibile() {
        if (alTelConnessi.size() < maxConnections)
            return true;
        return false;
    }

    public Cella connectTel(TelefonoBase t) throws OutOfMaxConnectionsException {
        if (connPossibile()) {
            alTelConnessi.add(t);
            return this;
        }
        throw new OutOfMaxConnectionsException("Raggiunto numero massimo di connessioni");
    }

    public Cella disconnectTel(TelefonoBase t) {
        alTelConnessi.remove(t);
        return this;
    }

    public TelefonoBase getConnTel(String numeroTelefono) {
        for (TelefonoBase t : alTelConnessi)
            if (t.getSim().getNumeroTelefono().endsWith(numeroTelefono))
                return t;
        return null;
    }


}
