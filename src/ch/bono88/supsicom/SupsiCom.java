package ch.bono88.supsicom;

import ch.bono88.utils.MyRandom;
import ch.bono88.utils.NumeroTelefono;

import ch.bono88.contratti.Abbonamento;
import ch.bono88.contratti.Prepagato;

import java.util.ArrayList;
import java.util.List;

public class SupsiCom {
    private List<Contratto> alContratti;
    private List<Cella> alCelle;
    private List<Utente> alClienti;
    private List<Segreteria> alSegreterie;

    public static final String SUPSICOM_NUMBER = "0000";

    public static final String SUPSICOM_PREFIX = "071";

    public SupsiCom() {
        alContratti = new ArrayList<>();
        alCelle = new ArrayList<>();
        alClienti = new ArrayList<>();
        alSegreterie = new ArrayList<>();
    }

    public void enableSegreteria(Sim s, boolean enable) {
        for (Contratto c : alContratti)
            if (c.getSim().equals(s))
                c.setHasSegreteriaAbilitata(enable);
    }

    public boolean hasSegreteria(Sim s) {
        for (Contratto c : alContratti)
            if (c.getSim().equals(s))
                return c.isHasSegreteriaAbilitata();
        return false;
    }

    public void addSegreteria(Segreteria s) {
        alSegreterie.add(s);
    }

    public List<Segreteria> getSegreterie(Sim s) {
        List<Segreteria> mySeg = new ArrayList<>();
        for (Segreteria seg : alSegreterie)
            if (seg.getRicevente().equals(s))
                mySeg.add(seg);

        //Tolgo tutti i messaggi in segreteria
        alSegreterie.removeAll(mySeg);

        return mySeg;
    }

    public void sendSUPSICOMSMS(String numero, String testo) throws Exception{
        TelefonoBase tRic = findTel(numero);

        //aggiungo l'sms al destinatario
        tRic.incSMS(SUPSICOM_NUMBER, testo);
    }

    public void requireSaldo(Sim s) throws Exception{
        if(s.getContratto() instanceof Prepagato)
            sendSUPSICOMSMS(s.getNumeroTelefono(),"Il suo saldo residuo sono "+((Prepagato) s.getContratto()).getSaldo() + " chf");
        else
            throw new Exception("L'abbonamento non è prepagato");
    }

    public Utente insertCliente(String strNome, String strCognome, int intCAP, double dAVS) throws Exception {
        for (Utente u : alClienti)
            if (u.getAVS() == dAVS)
                throw new Exception("AVS specificata già esistente");
        Utente u = new Utente(strNome, strCognome, intCAP, dAVS);
        alClienti.add(u);
        return u;
    }

    public TelefonoBase getTelFromNumber(String number) throws Exception {
        for (Contratto c : alContratti)
            if (c.getSim().getNumeroTelefono().equals(number))
                return c.getSim().getTel();
        throw new Exception("Numero non trovato");
    }

    public Cella getRandomCella() {
        MyRandom r = new MyRandom();
        int idCella = r.getIntInRange(0, alCelle.size());
        return alCelle.get(0);
    }

    public Sim insertContratto(Utente u, int contractType, int tariffaType) throws Exception {

        Sim s = new Sim(generaNumero());
        Contratto c;
        if (contractType == Contratto.TIPO_ABB)
            c = new Abbonamento(this,u, s, tariffaType);
        else
            c = new Prepagato(this,u, s, tariffaType);


        alContratti.add(c);
        s.setContratto(c);

        return s;

    }

    public Cella insertCella(int maxConnections) {
        Cella c = new Cella(this, maxConnections);
        alCelle.add(c);
        return c;
    }

    public List<Contratto> getContrattiForUser(Utente u) {
        List<Contratto> contr = new ArrayList<>();
        for (Contratto c : alContratti)
            if (c.getFirmatario().equals(u))
                contr.add(c);
        return contr;
    }

    public Utente getCliente(String nome, String cognome) throws Exception {

        for (Utente u : alClienti)
            if (u.getNome().equals(nome) && u.getCognome().equals(cognome))
                return u;
        throw new Exception("Il cliente cercato non è stato trovato");
    }

    public Utente getCliente(double nAvs) throws Exception {
        for (Utente u : alClienti)
            if (u.getAVS() == nAvs)
                return u;
        throw new Exception("Il cliente cercato non è stato trovato");
    }

    private NumeroTelefono generaNumero() {
        NumeroTelefono n;
        MyRandom r = new MyRandom();
        do {
            n = new NumeroTelefono(SUPSICOM_PREFIX, r.getIntInRange(1000000, 9999999));
        } while (checkNumberExists(n.toString()));

        return n;
    }

    private boolean checkNumberExists(String n) {
        for (Contratto c : alContratti)
            if (c.getSim().getNumeroTelefono().equals(n))
                return true;
        return false;
    }

    public TelefonoBase findTel(String numeroTel) throws Exception {
        TelefonoBase t;
        if (checkNumberExists(numeroTel))
            for (Cella c : alCelle) {
                t = c.getConnTel(numeroTel);
                if (t != null)
                    return t;
            }
        //se arrivo qui vuol dire che il telefono non è connesso ad alcuna cella
        for (Contratto c : alContratti)
            if (c.getSim().getNumeroTelefono().equals(numeroTel))
                return c.getSim().getTel();


            else
                throw new Exception("Numero inesistente");

        return null;
    }

}