package kap10_Exchanger_BlockingQueue.blockingqueue;

/**
 * Codebeispiel für einen Erzeuger von Zufallsstrings
 * mit einer BlockingQueue
 */
import java.util.concurrent.BlockingQueue;

import kap10_Exchanger_BlockingQueue.exchanger.Util;

public class RandomStringProducer implements Runnable
{
  private BlockingQueue<String> queue = null;
  private String endToken = null;

  public RandomStringProducer(BlockingQueue<String> queue,
      String endToken)
  {
    super();
    this.endToken = endToken;
    this.queue = queue;
  }

  public void run()
  {
    try
    {
      for (int i = 0; i < 2000; i++)
      {
        queue.put(Util.getRandomString(200000));
      }
      queue.put(endToken);
    } catch (InterruptedException ex)
    {
      ex.printStackTrace();
    }
  }
}
