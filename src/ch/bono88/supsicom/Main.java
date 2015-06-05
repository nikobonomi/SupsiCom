package ch.bono88.supsicom;

import ch.bono88.cellulari.NextGen;
import ch.bono88.contratti.Abbonamento;
import ch.bono88.contratti.Prepagato;
import ch.bono88.exceptions.*;
import java.io.IOException;

public class Main {

    public static void main(String[] args) {

        SupsiCom supsiCom = new SupsiCom();


        try {

            if (supsiCom.hasSavedState()) {
                supsiCom = supsiCom.loadState();

                Utente caricato1 = supsiCom.getCliente(123456789);
                Utente caricato2 = supsiCom.getCliente(987654321);

                TelefonoBase telU1 = caricato1.getTelefoni().get(0);
                TelefonoBase telU2 = caricato2.getTelefoni().get(0);

                telU1.call(telU2.getSim().getNumeroTelefono(),50);

                caricato1.printReg();

            }
            else {
                supsiCom.insertCella(5);
                supsiCom.insertCella(12);

                Utente u = supsiCom.insertCliente("Niko", "Bonomi", 6826, 123456789);
                TelefonoBase t = new NextGen();
                Sim s = supsiCom.insertContratto(u, Contratto.TIPO_ABB, Tariffe.TIPO_TAR_BASE);
                t.setSim(s.assocTelf(t));
                u.addTelefono(t);

                t.turnOn(supsiCom.getRandomCella());

                t.enableSegreteria(true);

                Utente u2 = supsiCom.insertCliente("Federico", "Carmine", 4568, 987654321);
                TelefonoBase t2 = new TelefonoBase();
                Sim s2 = supsiCom.insertContratto(u2, Contratto.TIPO_PRE, Tariffe.TIPO_TAR_TFRI);
                t2.setSim(s2.assocTelf(t2));
                u2.addTelefono(t2);

                t2.turnOn(supsiCom.getRandomCella());


                if (!t2.call(t.getSim().getNumeroTelefono(), 10)) {
                    //se la chiamata non è stata effettuata verifico
                    // e posso aggiungere un messaggio in segreteria
                    if (t.hasSegreteriaEnabled())
                        t.addSegreteria(t2.getSim().getNumeroTelefono(), "Messaggio di segreteria", 10);

                }

                t.sendSMS(t2.getSim().getNumeroTelefono(), "Ciao!! Questo è un sms!");
                t.call(t2.getSim().getNumeroTelefono(), 10);
                t2.call(t.getSim().getNumeroTelefono(), 4);

                t.turnOff();

                t2.call(t.getSim().getNumeroTelefono(), 4);

                t.turnOn(supsiCom.getRandomCella());

                t2.requireSaldo();

                for (Fattura f : ((Abbonamento) t.getSim().getContratto()).getFatture())
                    System.out.println(f);

                System.out.println("Utente 2");

                t2.cellaConnesso.getMaster().ricarica(t2.getSim().getNumeroTelefono(), Prepagato.RIC_50);
                t2.requireSaldo();
                u.printReg();
            }

            supsiCom.saveState();

        } catch (PhoneOfflineException e) {
            e.printStackTrace();
        } catch (OutOfMaxConnectionsException e) {
            e.printStackTrace();
        } catch (TariffaNotFoundException e) {
            e.printStackTrace();
        } catch (CustomerAleryExistException e) {
            e.printStackTrace();
        } catch (ContractTypeNotValidException e) {
            e.printStackTrace();
        } catch (NumberNotFoundException e) {
            e.printStackTrace();
        } catch (PhoneIdleException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (CustomerNotFoundException e) {
            e.printStackTrace();
        }


    }
}
