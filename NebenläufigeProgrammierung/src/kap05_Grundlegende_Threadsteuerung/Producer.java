package kap05_Grundlegende_Threadsteuerung;
/**
 * Codebeispiel für Erzeuger mit einem Ringpuffer
 */

import java.util.concurrent.ThreadLocalRandom;

class Producer implements Runnable
{
  private BoundedFIFOQueue<Integer> queue;

  public Producer(BoundedFIFOQueue<Integer> queue)
  {
    this.queue = queue;
  }

  @Override
  public void run()
  {
    try
    {
      for (int i = 0; i < 100; i++)
        queue.put(ThreadLocalRandom.current().nextInt(100));
    } catch (InterruptedException e)
    {
      // kann ignoriert werden
    }
  }
}
