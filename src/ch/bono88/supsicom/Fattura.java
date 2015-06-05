package ch.bono88.supsicom;

import ch.bono88.contratti.Abbonamento;
import sun.util.calendar.CalendarDate;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by Niko on 26.05.2015.
 */
public class Fattura implements Serializable{
    private static final long serialVersionUID = 9L;
    private List<Attivita> attivita;
    private float totalCost;
    private Date dataEmissione;
    private Abbonamento abbonamento;

    public Fattura(Attivita a, Abbonamento abbonamento) {
        totalCost = 0;
        attivita = new ArrayList<>();
        attivita.add(a);
        dataEmissione = new Date();
        this.abbonamento = abbonamento;
        totalCost +=a.getCost();
    }

    //verifico se l'attività è parte della fattura corrente
    public boolean attivitaIsFattura(Attivita a) {
        Calendar calFatt = Calendar.getInstance();
        calFatt.setTime(dataEmissione);
        Calendar calAtt = Calendar.getInstance();
        calAtt.setTime(a.getData());
        if (calFatt.get(Calendar.MONTH) == calAtt.get(Calendar.MONTH) &&
                calFatt.get(Calendar.YEAR) == calAtt.get(Calendar.YEAR))
            return true;
        return false;
    }

    //genero la stringa di output della fattura
    @Override
    public String toString() {
        Calendar calFatt = Calendar.getInstance();
        calFatt.setTime(dataEmissione);

        StringBuilder sb = new StringBuilder();
        StringBuilder sbSMS = new StringBuilder().append("Servizio: SMS\n");
        StringBuilder sbMMS = new StringBuilder().append("Servizio: MMS\n");

        StringBuilder sbCALL = new StringBuilder().append("Servizio: Chiamate\n");

        StringBuilder sbVID = new StringBuilder().append("Servizio: VideoChiamate\n");


        sb.append("Fattura SUPSICOM\nFattura del mese: ")
                .append(calFatt.get(Calendar.MONTH)+1)
                .append(" anno: ")
                .append(calFatt.get(Calendar.YEAR))
                .append("\n\n");

        sb.append("Riguardante il numero ").append(abbonamento.getSim().getNumeroTelefono());
        sb.append("\nIn abbonamente al clinete ")
                .append(abbonamento.getFirmatario().getNome())
                .append(" ")
                .append(abbonamento.getFirmatario().getCognome())
                .append("\n\n");

        for (Attivita a : attivita)
            if (a.getType() == Attivita.TYPE_SMS)
                sbSMS.append(a.getData())
                        .append("\t")
                        .append("a:")
                        .append(a.getFrom())
                        .append("\t")
                        .append("costo: ")
                        .append(a.getCost())
                        .append("\n");
            else if (a.getType() == Attivita.TYPE_MMS)
                sbMMS.append(a.getData())
                        .append("\t")
                        .append("a:")
                        .append(a.getFrom())
                        .append("\t")
                        .append("costo: ")
                        .append(a.getCost())
                        .append("\n");
            else if (a.getType() == Attivita.TYPE_VIDEO)
                sbVID.append(a.getData())
                        .append("\t")
                        .append("a:")
                        .append(a.getFrom())
                        .append("\t")
                        .append("durata:")
                        .append(a.getDurata())
                        .append("\t")
                        .append("costo: ")
                        .append(a.getCost())
                        .append("\n");
            else if (a.getType() == Attivita.TYPE_CALL)
                sbCALL.append(a.getData())
                        .append("\t")
                        .append("a:")
                        .append(a.getFrom())
                        .append("\t")
                        .append("durata:")
                        .append(a.getDurata())
                        .append("\t")
                        .append("costo: ")
                        .append(a.getCost())
                        .append("\n");

        sb.append(sbSMS).append(sbMMS).append(sbCALL).append(sbVID);

        sb.append("\n\nTotale: ").append(totalCost).append(" chf.\n");

        return sb.toString();
    }

    public List<Attivita> getAttivita() {
        return this.attivita;
    }

    public void addAttivita(Attivita a) {
        attivita.add(a);
        totalCost +=a.getCost();
    }

    public Date getDataEmissione() {
        return dataEmissione;
    }

    public float getTotalCost() {
        return this.totalCost;
    }
}
