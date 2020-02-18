package kap13_ForkJoinFramework;

/**
 * Codebeispiel für die Verwendung von RecursiveAction
 */

import java.util.Arrays;
import java.util.concurrent.ForkJoinPool;

public class RandomInitTaskDriver
{

  public static void main(String[] args)
  {
    int[] array = new int[42];

    ForkJoinPool fjPool = new ForkJoinPool();
    RandomInitTask root = new RandomInitTask(array, 0, array.length, 100);
    fjPool.invoke(root);

    // Falls der commonPool benutzt werden soll
    // root.invoke();

    System.out.println(Arrays.toString(array));
  }

}
