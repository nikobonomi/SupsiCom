package ch.bono88.storico;

import java.io.Serializable;
import java.util.Date;


public class SMS implements Serializable{
    private String numero, message;
    private Date   data;
    private boolean isMMS, isIncoming;
    private static final long serialVersionUID = 5L;

    public SMS(String numero, boolean isMMS, String message, boolean isIncoming) {
        this.numero = numero;
        this.data = new Date();
        this.isMMS = isMMS;
        this.message = message;
        this.isIncoming = isIncoming;
    }

    public boolean isMMS() {
	System.out.println("ciao")
        return this.isMMS;
    }
  
  	  @Override
  public String toString(){
          StringBuilder sb = new StringBuilder();

          if (isIncoming)
              sb.append("DA:");
          else sb.append("A:");

          sb
                  .append(numero)
                  .append(" Mssaggio: ")
                  .append(message)
                  .append(" Data")
                  .append(data);

          return sb.toString();


  }

}
