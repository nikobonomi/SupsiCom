package ch.bono88.supsicom;

import ch.bono88.supsicom.TelefonoBase;
import ch.bono88.utils.NumeroTelefono;

public class Sim {
    private NumeroTelefono numeroTelefono;
    private TelefonoBase   telefonoAssociato;
    private Contratto contratto;

    public Sim(NumeroTelefono numeroTelefono) {
        this.numeroTelefono = numeroTelefono;
    }

    public Sim assocTelf(TelefonoBase t) {
        this.telefonoAssociato = t;
        return this;
    }

    public TelefonoBase getTel() {
        return this.telefonoAssociato;
    }

    public String getNumeroTelefono() {
        return this.numeroTelefono.toString();
    }

    public Contratto getContratto() {
        return contratto;
    }

    public void setContratto(Contratto contratto) {
        this.contratto = contratto;
    }
}
