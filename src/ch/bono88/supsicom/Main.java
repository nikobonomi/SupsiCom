package ch.bono88.supsicom;

import ch.bono88.cellulari.NextGen;

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


            Utente u2 = supsiCom.insertCliente("Federico", "Carmine", 4568, 987654321);
            TelefonoBase t2 = new TelefonoBase();
            Sim s2 = supsiCom.insertContratto(u2, Contratto.TIPO_ABB, Tariffe.TIPO_TAR_BASE);
            t2.setSim(s2.assocTelf(t2));
            u2.addTelefono(t2);
            t2.turnOn(supsiCom.getRandomCella());


            t2.call(t.getSim().getNumeroTelefono(), 10);

            t.sendSMS(t2.getSim().getNumeroTelefono(), "Ciao!! Questo è un sms!");

            t.turnOff();

            t2.call(t.getSim().getNumeroTelefono(), 10);

            t.turnOn(supsiCom.getRandomCella());

            u2.printReg();


        } catch (Exception e) {
          e.printStackTrace();
        }


    }
}
