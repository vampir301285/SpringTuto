package kap10_Exchanger_BlockingQueue.blockingqueue;

/**
 * Codebeispiel für einen Verbraucher von Zufallsstrings
 * mit einer BlockingQueue
 */
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;

import kap10_Exchanger_BlockingQueue.exchanger.Util;

public class RandomStringConsumer implements Callable<int[]>
{
  private BlockingQueue<String> queue = null;
  private String endToken = null;

  public RandomStringConsumer(BlockingQueue<String> queue,
      String endToken)
  {
    super();
    this.queue = queue;
    this.endToken = endToken;
  }

  public int[] call()
  {
    try
    {
      int[] charFrequency = new int[Util.BUCHSTABEN.length()];

      String str = null;
      while (true)
      {
        str = queue.take();
        if (str.equals(endToken))
        {
          break;
        }
        count(charFrequency, str);
      }

      return charFrequency;
    } catch (Exception ex)
    {
      ex.printStackTrace();
      return null;
    }
  }

  private static void count(int[] charFrequency, String str)
  {
    for (int i = 0; i < str.length(); i++)
    {
      char c = str.charAt(i);
      int pos = Util.BUCHSTABEN.indexOf(c);
      if (pos != -1)
      {
        charFrequency[pos]++;
      }
    }
  }
}
