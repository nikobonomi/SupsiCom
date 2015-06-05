package ch.bono88.utils;

import java.io.Serializable;

public class NumeroTelefono implements Serializable{
  private static final long serialVersionUID = 20L;
  private String prefix;
  int number;
  
  public NumeroTelefono(String prefix, int number){
    this.prefix = prefix;
    this.number = number;
  }

  public String toString(){
   return new StringBuilder().append(prefix).append(number).toString();
  }  
}