package ch.bono88.supsicom;

import ch.bono88.cellulari.Evoluto;
import ch.bono88.contratti.Abbonamento;
import ch.bono88.contratti.Prepagato;
import ch.bono88.exceptions.*;
import ch.bono88.storico.Chiamata;
import ch.bono88.storico.SMS;

import java.util.ArrayList;
import java.util.List;

public class TelefonoBase {
    protected Sim sim;
    protected boolean isOn;
    protected Cella cellaConnesso;
    protected List<Chiamata> regChiamate;
    protected List<SMS> regSMS;
    protected boolean isConnected;
    protected List<SMS> avvisoSMS;
    protected List<Segreteria> alSegreterie;


    public TelefonoBase() {
        this.isOn = false;
        this.regChiamate = new ArrayList<>();
        this.regSMS = new ArrayList<>();
        this.avvisoSMS = new ArrayList<>();
    }

    public void connectCella(Cella c) throws OutOfMaxConnectionsException {
        if (c.connPossibile()) {
            this.cellaConnesso = c;
            c.connectTel(this);
        } else
            throw new OutOfMaxConnectionsException("Raggiunto numero massimo connessioni per cella");
    }

    public void enableSegreteria(boolean enable)throws PhoneOfflineException{
        if(cellaConnesso ==null)
            throw new PhoneOfflineException("Il telefono deve essere connesso per poter attivare la segreteria!!");
        cellaConnesso.getMaster().enableSegreteria(sim,enable);
    }

    public boolean hasSegreteriaEnabled(){
        return cellaConnesso.getMaster().hasSegreteria(sim);
    }

    public boolean call(String numero, int durata) throws NumberNotFoundException, PhoneOfflineException, PhoneIdleException, TariffaNotFoundException {
        TelefonoBase tRic = cellaConnesso.getMaster().findTel(numero);

        if (!tRic.isOn())

            //se il telefono non è di base allora posso lasciare un'avviso di chiamata
            //cerco il telefono direttamente da supsicom senza passare dalle celle

            if (tRic instanceof Evoluto)
                ((Evoluto) tRic).addToAvvisoCall(numero);
            else
                throw new PhoneOfflineException("Telefono spento");

        else
            //telefono acceso
            if (!tRic.isConnected()) {

                //mi connetto al telefono
                tRic.connect();

                //creo la chiamata
                Chiamata c = new Chiamata(numero, durata, false, false);

                //registro la chiamata corrente nel registro
                addToRegCall(c);
                tRic.incCall(this.sim.getNumeroTelefono(), durata);

                //accredito la chiamata
                if(sim.getContratto() instanceof Prepagato){
                    ((Prepagato) sim.getContratto()).accreditaChiamata(c);
                }
                else{
                    ((Abbonamento) sim.getContratto()).accreditaChiamata(c);
                }

                //chiudo la comunicazione
                tRic.disconnect();

                return true;
            } else
                throw new PhoneIdleException("Telefono occupato");


        return false;

    }

    public void incCall(String numero, int durata) throws PhoneOfflineException {
        if (isOn())
            addToRegCall(numero, durata, true);
        else
            throw new PhoneOfflineException("Il telefono è spento. Non può ricevere chiamate.");
    }

    public void sendSMS(String numero, String testo) throws NumberNotFoundException {
        TelefonoBase tRic = cellaConnesso.getMaster().findTel(numero);

        //non faccio la connessione al telefono e non controllo che sia acceso
        //perchè i messaggi arrivano anche se il telefono ha una chiamata in corso
        //perchè se il telefono è spento i messaggi arrivano subito appena acceso

        //salvo l'sms nel registro del telefono
        addToRegSMS(numero, testo, false);

        //accredito l'sms
        if(sim.getContratto() instanceof Prepagato){
            ((Prepagato) sim.getContratto()).accreditaSMS();
        }
        else{
           ((Abbonamento) sim.getContratto()).accreditaSMS(numero);
        }

        //aggiungo l'sms al destinatario
        tRic.incSMS(this.sim.getNumeroTelefono(), testo);

    }

    public void requireSaldo() throws NumberNotFoundException, ContractTypeNotValidException {
        getCellaConnesso().getMaster().requireSaldo(sim);
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

    public void turnOn(Cella c) throws OutOfMaxConnectionsException {

        this.isOn = true;
        //se ci sono sms in attesa di ricezione li "ricevo"
        if (avvisoSMS.size() > 0) {
            for (SMS s : avvisoSMS)
                addToRegSMS(s);
            avvisoSMS.clear();
        }
        connectCella(c);

       List<Segreteria> seg = cellaConnesso.getMaster().getSegreterie(sim);

        if(seg.size()>0)
            alSegreterie.addAll(seg);
    }

    public void turnOff() {
        this.isOn = false;
        cellaConnesso.disconnectTel(this);
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

    public void addSegreteria(String num, String message, int durata){
        cellaConnesso.getMaster().addSegreteria(new Segreteria(message, num, sim, durata));
    }

    public List<Chiamata> getRegChiamate() {
        return regChiamate;
    }

    public List<SMS> getRegSMS() {
        return regSMS;
    }
}
