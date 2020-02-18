package kap14_Arrays_und_Streams;


import java.util.Arrays;


public class ArraySort
{
  public static void main(String[] args)
  {
    String[] strArray = new String[]{"xyz","abx","mnl","k","kk"};
    
    Arrays.parallelSort(strArray, ( x, y ) -> y.compareTo( x ) );

    System.out.println( Arrays.toString(strArray));
  }

}
