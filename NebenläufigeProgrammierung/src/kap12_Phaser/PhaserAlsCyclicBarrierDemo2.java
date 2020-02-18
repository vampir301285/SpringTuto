package kap12_Phaser;

/**
 * Codebeispiel: Phaser als CyclicBarrier. Hier wird die Rundenzahl über die 
 * Phase gesteuert, wobei die Threads die Phase explizit abfragen
 */

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Phaser;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

public class PhaserAlsCyclicBarrierDemo2
{
  static class Athlet implements Runnable
  {
    private static final int ROUND = 5;
    private final String name;
    private final Phaser phaser;

    public Athlet(String name, Phaser phaser)
    {
      this.name = name;
      this.phaser = phaser;
    }

    @Override
    public void run()
    {
      System.out.println(name + " ist bereit ....");
      // Warte auf das Startsignal
      int phase = 0;
      while( phase < ROUND)
      {
        phaser.arrive();
        phase = phaser.awaitAdvance(phase);
        // Alternative
        // phase = phaser.arriveAndAwaitAdvance();
        
        
        System.out.println(name + " startet ....");
        randomDelay(1000);
        System.out.println(name + " ist am Ziel ");
      }
    }

    private void randomDelay(int ms)
    {
      try
      {
        TimeUnit.MILLISECONDS.sleep(ThreadLocalRandom.current().nextInt(ms));
      }
      catch (InterruptedException e)
      {
        Thread.currentThread().interrupt();
      }
    }
  }

  public static void main(String[] args) throws InterruptedException
  {
    ExecutorService executor = Executors.newFixedThreadPool(3);

    Phaser startlinie = new Phaser(3);
    Athlet a1 = new Athlet("Card Lewis", startlinie);
    Athlet a2 = new Athlet("Maurice Greene", startlinie);
    Athlet a3 = new Athlet("Usain Bolt", startlinie);

    executor.execute(a1);
    executor.execute(a2);
    executor.execute(a3);

    executor.shutdown();
  }
}
