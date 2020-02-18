package kap13_ForkJoinFramework;

/**
 * Codebeispiel zu RecursiveTask für die Summation eines int-Arrays
 */

import java.util.concurrent.RecursiveTask;

@SuppressWarnings("serial")
public class SumTask extends RecursiveTask<Integer>
{
  private final int THRESHOLD = 4;
  
  private final int[] array;
  private final int min;
  private final int max;
  
  SumTask(int[] array, int min, int max)
  {
    this.array = array;
    this.min = min;
    this.max = max;
  }

  @Override
  protected Integer compute()
  {
    if( (max - min) <= THRESHOLD )
    {
      int count = 0;
      for(int i = min; i < max; i++)
      {
        count += array[i];
      }
      return count;
    }
    else
    {
      int mid = min + (max-min)/2;
      SumTask left = new SumTask(array,min, mid );
      SumTask right = new SumTask(array,mid, max );
      invokeAll(left, right);
      
      return left.join() + right.join();
    }
  }
}

