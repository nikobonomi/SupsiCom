package ch.bono88.supsicom;

import ch.bono88.cellulari.NextGen;
import ch.bono88.contratti.Abbonamento;
import ch.bono88.contratti.Prepagato;
import ch.bono88.tariffe.TopFriend;

import java.io.Console;
import java.util.logging.ConsoleHandler;

public class Main {

    public static void main(String[] args) {

        SupsiCom supsiCom = new SupsiCom();


        try {

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
            u2.printReg();


        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
