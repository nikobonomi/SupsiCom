package ch.bono88.exceptions;

/**
 * Created by Niko on 01.06.2015.
 */
public class OutOfMaxConnectionsException extends Exception {
    public OutOfMaxConnectionsException(String s){
        super(s);
    }
}
