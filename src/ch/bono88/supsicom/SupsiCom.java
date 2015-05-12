package ch.bono88.supsicom;

import ch.bono88.utils.MyRandom;
import ch.bono88.utils.NumeroTelefono;

import ch.bono88.contratti.Abbonamento;
import ch.bono88.contratti.Prepagato;

import java.util.ArrayList;

public class SupsiCom {
  private ArrayList<Contratto> alContratti;
  private ArrayList<Cella> alCelle;
  private ArrayList<Utente> alClienti;
  
  public static final String SUPSICOM_PREFIX = "071";
  
    public SupsiCom() {
        alContratti = new ArrayList<Contratto>();
        alCelle = new ArrayList<Cella>();
        alClienti = new ArrayList<Utente>();
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

        if (contractType == Contratto.TIPO_ABB)
            alContratti.add(new Abbonamento(u, s, tariffaType));
        else if (contractType == Contratto.TIPO_PRE)
            alContratti.add(new Prepagato(u, s, tariffaType));

        return s;

    }

    public Cella insertCella(int maxConnections) {
        Cella c = new Cella(this, maxConnections);
        alCelle.add(c);
        return c;
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
                if (t!= null)
                    return t;
            }
            //se arrivo qui vuol dire che il telefono non è connesso ad alcuna cella
            for(Contratto c: alContratti)
                if(c.getSim().getNumeroTelefono().equals(numeroTel))
                    return c.getSim().getTel();


        else
            throw new Exception("Numero inesistente");
        
      return null;
  }
  
}