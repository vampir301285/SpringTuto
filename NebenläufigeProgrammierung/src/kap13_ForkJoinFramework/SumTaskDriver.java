package kap13_ForkJoinFramework;

/**
 * Codebeispiel für die Verwendung von RecursiveTask
 */

import java.util.concurrent.ForkJoinPool;

public class SumTaskDriver
{
  public static void main(String[] args)
  {
    int[] array = new int[42];
    
    ForkJoinPool fjPool = new ForkJoinPool();
    
    // Befüllen des Array (RecursiveAction)
    RandomInitTask task = new RandomInitTask(array,0, array.length, 100);
    fjPool.invoke(task);

    // Summation (RecursiveTask)
    SumTask root = new SumTask(array,0, array.length);
    fjPool.invoke(root); 
    System.out.println("Summe " + root.join() );
  }
}
