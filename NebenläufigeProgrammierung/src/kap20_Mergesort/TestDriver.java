package kap20_Mergesort;

/**
 * Demonstriert das Sortieren mit dem Mergesort
 * Eventuell muss der Heapspace der VM vergrößert werden
 */

import java.util.Arrays;
import java.util.Random;


public class TestDriver
{
  public static void main(String[] args)
  {
    final int RUNS = 5;
    final int N = 10_000_000;
    Random rd = new Random();

    Integer[] intArray1 = new Integer[N];
    Integer[] intArray2 = new Integer[N];
    Integer[] intArray3 = new Integer[N];
    Integer[] intArray4 = new Integer[N];
    
    for (int j = 0; j < RUNS; j++)
    {
      System.out.println("Befülle vier Arrays");
      for (int i = 0; i < N; i++)
      {
        Integer k = new Integer(rd.nextInt(1000));
        intArray1[i] = k;
        intArray2[i] = k;
        intArray3[i] = k;
        intArray4[i] = k;
      }

      System.out.println("Starte Sortierung");
      long start, end;

      // Serieller Mergsort
      start = System.currentTimeMillis();
      Merge.sort(intArray1);
      end = System.currentTimeMillis();
      System.out.println("Merge.sort : " + (end - start) + "[ms]");

      // Paralleler Mergsort
      start = System.currentTimeMillis();
      ParallelMerge.sort(intArray2);
      end = System.currentTimeMillis();
      System.out.println("ParallelMerge.sort : " + (end - start) + "[ms]");

      // Standard Arrays.sort(..)
      start = System.currentTimeMillis();
      Arrays.sort(intArray3);
      end = System.currentTimeMillis();
      System.out.println("Arrays.sort : " + (end - start) + "[ms]");

      // Standard Arrays.parallelSort(..)
      start = System.currentTimeMillis();
      Arrays.parallelSort(intArray4);
      end = System.currentTimeMillis();
      System.out.println("Arrays.parallelSort : " + (end - start) + "[ms]");

      System.out.println("check");
      for (int i = 0; i < N; i++)
      {
        if( intArray1[i].equals(intArray2[i]) == false 
            || intArray1[i].equals(intArray3[i]) == false 
            || intArray1[i].equals(intArray4[i]) == false )
        {
          System.out.println("Fehler bei Index " + i);
          System.out.println(intArray4[i]);
          System.out.println(intArray3[i]);
          System.out.println(intArray2[i]);
          System.out.println(intArray1[i]);
          break;
        }
      }
      System.out.println();
    }
    System.out.println("done");
  }
}
