package ch.bono88.utils;

public class InvocReturn {
  private boolean hasError;
  private Object ret;
  
  public InvocReturn(boolean hasError, Object ret){
    this.hasError = hasError;
    this.ret = ret;
  }
  
  public boolean hasError(){
    return hasError;
  }
  
  public Object getRet(){
    return ret;
  }
  
}
