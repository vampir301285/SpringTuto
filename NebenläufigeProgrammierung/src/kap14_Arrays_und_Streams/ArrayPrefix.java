package kap14_Arrays_und_Streams;

import java.util.Arrays;

public class ArrayPrefix
{

  public static void main(String[] args)
  {
    double[] array = new double[]{0.1, 0.3, 0.4, 0.2};
    Arrays.parallelPrefix(array, (i,j) -> i + j );
    
    System.out.println( Arrays.toString(array) );
    
  }

}
