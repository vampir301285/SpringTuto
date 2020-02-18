package kap11_CountDownLatch_CyclicBarrier;

/**
 * Codebeispiel: CountDownLatch mit dem Startsignal vom zuletzt ankommenden Läufer
 */

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

public class CountDownDemo2
{
  static class Athlet implements Runnable 
  {

    private String name;
    private CountDownLatch latch;

    public Athlet(String name, CountDownLatch latch)
    {
      this.name = name;
      this.latch = latch;
    }

    @Override
    public void run()
    {
      System.out.println(name + " ist bereit ....");
      try
      {
        // Warte auf das Startsignal
        latch.countDown();
        latch.await();
        TimeUnit.MILLISECONDS.sleep(ThreadLocalRandom.current().nextInt(1000));
        System.out.println(name + " ist am Ziel ");
      }
      catch (InterruptedException ex)
      {
        System.out.println("Wettkampf abgebrochen!");
      }
    }
  }

  public static void main(String[] args) throws InterruptedException
  {
    ExecutorService executor = Executors.newFixedThreadPool(3);
    CountDownLatch startlinie = new CountDownLatch(3);
    Athlet a1 = new Athlet("Carl Lewis", startlinie);
    Athlet a2 = new Athlet("Maurice Greene", startlinie);
    Athlet a3 = new Athlet("Usain Bolt", startlinie);
    executor.execute(a1);
    executor.execute(a2);
    executor.execute(a3);
    
    executor.shutdown();
  }
}
