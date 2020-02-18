package kap05_Grundlegende_Threadsteuerung;

/**
 * Codebeispiel für Verbraucher mit einem Ringpuffer
 *
 */
class Consumer implements Runnable
{
  private BoundedFIFOQueue<Integer> queue;
  private int count = 0;

  Consumer(BoundedFIFOQueue<Integer> queue)
  {
    this.queue = queue;
  }

  @Override
  public void run()
  {
    try
    {
      while (Thread.currentThread().isInterrupted() == false)
      {
        System.out.println(queue.get());
        count++;
      }
    }

    catch (InterruptedException exce)
    {
      // kann ignoriert werden
    }
    System.out.println("Anzahl: " + count);
  }
}
