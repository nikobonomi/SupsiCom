package ch.bono88.utils;

import java.io.Serializable;
import java.util.Random;

public class MyRandom extends Random implements Serializable{

  private static final long serialVersionUID = 18L;
  
    public MyRandom() {
        super();
    }

    public int getIntInRange(int min, int max) {
        int n = max - min + 1;
        int i = nextInt() % n;
        return min + i;
    }


}
