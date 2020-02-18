package kap14_Arrays_und_Streams;

import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.IntUnaryOperator;

public class ArrayTransform
{
  public static void main(String[] args)
  {
    int[] array = new int[1000];
    
    Arrays.parallelSetAll(array, (i) -> i );
    
    IntUnaryOperator op = (i) -> ThreadLocalRandom.current().nextInt(100);
    Arrays.parallelSetAll(array, op );

    System.out.println( Arrays.toString(array) ); 
    
    
    String[] strArray = new String[] {"abc", "xyz" };
    Arrays.parallelSetAll(strArray, (i) -> strArray[i].toUpperCase() );
    
    System.out.println( Arrays.toString(strArray) );
  }

}
