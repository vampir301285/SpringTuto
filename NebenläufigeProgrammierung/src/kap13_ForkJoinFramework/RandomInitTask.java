package kap13_ForkJoinFramework;


/**
 * Codebeispiel zu RecursiveAction für die Initialisierung eines int-Arrays
 */

import java.util.concurrent.RecursiveAction;
import java.util.concurrent.ThreadLocalRandom;

@SuppressWarnings("serial")
public class RandomInitTask extends RecursiveAction
{
  private final int THRESHOLD = 4;
  
  private final int[] array;
  private final int min;
  private final int max;
  private final int rdMax;
  
  public RandomInitTask(int[] array, int min, int max, int rdBound)
  {
    this.array = array;
    this.min = min;
    this.max = max;
    this.rdMax = rdBound;
  }

  @Override
  protected void compute()
  {
    if( (max - min) <= THRESHOLD )
    {
      for(int i = min; i < max; i++)
      {
        array[i] = ThreadLocalRandom.current().nextInt( rdMax );
      }
    }
    else
    {
      int mid = min + (max-min)/2;
      RandomInitTask left = new RandomInitTask(array,min, mid, rdMax );
      RandomInitTask right = new RandomInitTask(array,mid, max, rdMax );
      invokeAll(left, right);
    }
  }
}
