package ch.bono88.utils;

public class NumeroTelefono {
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