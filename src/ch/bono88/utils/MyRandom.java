package ch.bono88.utils;

import java.util.Random;

public class MyRandom extends Random {

  private static final long serialVersionUID = 1L;
  
    public MyRandom() {
        super();
    }

    public int getIntInRange(int min, int max) {
        int n = max - min + 1;
        int i = nextInt() % n;
        return min + i;
    }


}
