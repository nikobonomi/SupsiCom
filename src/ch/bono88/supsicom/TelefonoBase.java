package ch.bono88.supsicom;

import ch.bono88.storico.Chiamata;
import ch.bono88.storico.SMS;

import java.util.ArrayList;

public class TelefonoBase {
    protected Sim sim;
    protected boolean isOn;
    protected Cella cellaConnesso;
    protected ArrayList<Chiamata> regChiamate;
    protected ArrayList<SMS> regSMS;
    protected boolean isConnected;
    protected ArrayList<SMS> avvisoSMS;


    public TelefonoBase() {
        this.isOn = false;
        this.regChiamate = new ArrayList<>();
        this.regSMS = new ArrayList<>();
        this.avvisoSMS = new ArrayList<>();
    }

    public void connectCella(Cella c) throws Exception {
        if (c.connPossibile()) {
            this.cellaConnesso = c;
            c.connectTel(this);
        }
        else
            throw new Exception("Raggiunto numero massimo connessioni per cella");
    }



    public void call(String numero, int durata) throws Exception {
        TelefonoBase tRic = cellaConnesso.getMaster().findTel(numero);

        if (tRic.equals(null)) {
            throw new Exception("Telefono spento");
            //se il telefono non è di base allora posso lasciare un'avviso di chiamata
            //cerco il telefono direttamente da supsicom senza passare dalle celle
        } else {
            //telefono acceso
            if (!tRic.isConnected()) {

                //mi connetto al telefono
                tRic.connect();

                //registro la chiamata corrente nel registro
                addToRegCall(numero, durata, false);
                tRic.incCall(numero, durata);

                //chiudo la comunicazione
                tRic.disconnect();

            } else {
                throw new Exception("Telefono occupato");
            }
        }

    }

    public void incCall(String numero, int durata) throws Exception {
        if (isOn())
            addToRegCall(numero, durata, true);
        else
            throw new Exception("Il telefono è spento. Non può ricevere chiamate.");
    }

    public void sendSMS(String numero, String testo) throws Exception {
        TelefonoBase tRic = cellaConnesso.getMaster().findTel(numero);

        //non faccio la connessione al telefono e non controllo che sia acceso
        //perchè i messaggi arrivano anche se il telefono ha una chiamata in corso
        //perchè se il telefono è spento i messaggi arrivano subito appena acceso

        //salvo l'sms nel registro del telefono
        addToRegSMS(numero, testo, false);

        //aggiungo l'sms al destinatario
        tRic.incSMS(numero, testo);

    }

    public void incSMS(String numero, String messaggio) {
        //Se il telefono è acceso aggiungo il messaggio all'arrayList di messaggi ricevuti.
        //Altrimenti lo aggiungo agli avvisi. In modo che quando venga accesso ricevo il messaggio
        if (isOn())
            addToRegSMS(numero, messaggio, true);
        else
            addToAvvisoSMS(numero, messaggio);
    }

    public void setSim(Sim s) {
        this.sim = s;
    }

    public Sim getSim() {
        return this.sim;
    }

    public Cella getCellaConnesso() {
        return this.cellaConnesso;
    }

    public void turnOn(Cella c) throws Exception {
        this.isOn = true;
        //se ci sono sms in attesa di ricezione li "ricevo"
        if (avvisoSMS.size() > 0) {
            for (SMS s : avvisoSMS)
                addToRegSMS(s);
            avvisoSMS.clear();
        }

        connectCella(c);
    }

    public void turnOff() {
        this.isOn = false;
    }

    public boolean isOn() {
        return this.isOn;
    }

    public boolean isConnected() {
        return isConnected;
    }

    public void connect() {
        this.isConnected = true;
    }

    public void disconnect() {
        this.isConnected = false;
    }

    public void addToRegCall(String numero, int durata, boolean isIncoming) {
        this.regChiamate.add(new Chiamata(numero, durata, false, isIncoming));
    }

    public void addToRegCall(Chiamata c) {
        this.regChiamate.add(c);
    }

    public void addToRegSMS(String numero, String messaggio, boolean isIncoming) {
        regSMS.add(new SMS(numero, false, messaggio, isIncoming));
    }

    public void addToRegSMS(SMS s) {
        regSMS.add(s);
    }

    public void addToAvvisoSMS(String numero, String messaggio) {
        avvisoSMS.add(new SMS(numero, false, messaggio, true));
    }

    public ArrayList<Chiamata> getRegChiamate() {
        return regChiamate;
    }

    public ArrayList<SMS> getRegSMS() {
        return regSMS;
    }
}
