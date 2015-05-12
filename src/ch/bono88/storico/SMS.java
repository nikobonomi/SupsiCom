package ch.bono88.storico;

import java.util.Date;


public class SMS {
    private String numero, message;
    private Date   data;
    private boolean isMMS, isIncoming;

    public SMS(String numero, boolean isMMS, String message, boolean isIncoming) {
        this.numero = numero;
        this.data = new Date();
        this.isMMS = isMMS;
        this.message = message;
        this.isIncoming = isIncoming;
    }

    public String getMessage() {
        return this.message;
    }

    public String getNumero() {
        return this.numero;
    }

    public Date getData() {
        return this.data;
    }

    public boolean isIncoming() {
        return this.isIncoming;
    }

    public boolean isMMS() {
        return this.isMMS;
    }
  
  	  @Override
  public String toString(){
    return new StringBuilder()
      .append("Da: ")
      .append(numero)
      .append(" Mssaggio: ")
      .append(message)
      .append(" Data")
      .append(data)
      .toString();
  }

}
