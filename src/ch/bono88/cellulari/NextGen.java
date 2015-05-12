package ch.bono88.cellulari;

import java.util.ArrayList;

import ch.bono88.storico.Chiamata;
import ch.bono88.storico.SMS;
import ch.bono88.supsicom.Sim;
import ch.bono88.supsicom.TelefonoBase;

public class NextGen extends Evoluto {

    protected ArrayList<Chiamata> avvisiVideoChiamate;
    protected ArrayList<SMS>      avvisiMMS;


    public NextGen() {
        super();
    }


    public void videoCall(String numero, int durata) throws Exception {
        TelefonoBase tRic = cellaConnesso.getMaster().findTel(numero);

        if (tRic.equals(null)) {
            throw new Exception("Raggiunto numero massimo connessioni per cella");
        } else {
            //telefono acceso

            if (tRic instanceof NextGen) {
                if (!tRic.isConnected()) {
                    //mi connetto al telefono
                    tRic.connect();

                    //registro la videochiamata corrente nel registro
                    addToRegVideoCall(numero, durata, false);
                    ((NextGen)tRic).addToRegVideoCall(numero, durata, true);

                    //chiudo la comunicazione
                    tRic.disconnect();
                } else {
                    //notifico la chiamata dato che è occupato
                    ((NextGen)tRic).addToAvvisoVideoCall(numero);
                }
            } else {
                throw new Exception("Il telefono non supporta le videochiamate");
            }

        }

    }

    public void incVideoCall(String numero, int durata) {
        //implemento il sistema di avviso chiamata in caso che il telefono è spento
        if (isOn())
            addToRegVideoCall(numero, 0, true);
        else
            addToAvvisoVideoCall(numero);
    }

    public void sendMMS(String numero, String testo) throws Exception {
      
      TelefonoBase tRic = cellaConnesso.getMaster().findTel(numero);

      if (tRic instanceof NextGen) {
      
         //salvo l'mms nel registro del telefono
        addToRegMMS(numero, testo, false);
        
        //aggiungo l'mms al destinatario
        ((NextGen) tRic).incMMS(numero, testo);
        
      }
      else
        throw new Exception("Il telefono non supporta gli mms");

    }


    public void incMMS(String numero, String messaggio) {
        //Se il telefono è acceso aggiungo il messaggio all'arrayList di messaggi ricevuti.
        //Altrimenti lo aggiungo agli avvisi. In modo che quando venga accesso ricevo il messaggio
        if (isOn())
            addToRegMMS(numero, messaggio, true);
        else
            addToAvvisoMMS(numero, messaggio);
    }


    public void addToAvvisoVideoCall(String numero) {
        //la durata 0 identifica la chiamata persa
        this.avvisiVideoChiamate.add(new Chiamata(numero, 0, true, true));
    }

    public void addToRegVideoCall(String numero, int durata, boolean isIncoming) {
        this.regChiamate.add(new Chiamata(numero, durata, true, isIncoming));
    }

    public void addToRegMMS(String numero, String messaggio, boolean isIncoming) {
        regSMS.add(new SMS(numero, true, messaggio, isIncoming));
    }

    public void addToAvvisoMMS(String numero, String messaggio) {
        avvisiMMS.add(new SMS(numero, true, messaggio, true));
    }
  

}
