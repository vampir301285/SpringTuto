package kap11_CountDownLatch_CyclicBarrier;

/**
 * Codebeispiel: Anwendung einer CyclicBarrier
 */

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

public class CyclicBarrierDemo
{
  static class Athlet implements Runnable
  {
    private final String name;
    private final CyclicBarrier barrier;

    public Athlet(String name, CyclicBarrier barrier)
    {
      this.name = name;
      this.barrier = barrier;
    }

    @Override
    public void run()
    {
      System.out.println(name + " ist bereit ....");
      try
      {
        int time = 0;
        for (int i = 0; i < ROUND; i++)
        {
          // Warte auf Mitläufer für eine neue Runde
          barrier.await();
          int lauf = ThreadLocalRandom.current().nextInt(1000);
          TimeUnit.MILLISECONDS.sleep(lauf);
          time += lauf;
        }
        // Warte auf das Ende des Wettkampfs
        barrier.await();
        System.out.println(name + " ist am Ziel : " + time + " Gesamtzeit");
      }
      catch (InterruptedException | BrokenBarrierException ex)
      {
        System.out.println("Wettkampf abgebrochen!");
      }
    }
  }

  private static final int ROUND = 3;

  public static void main(String[] args)
  {
    final int taskCount = 3;
    ExecutorService executor = Executors.newFixedThreadPool(taskCount);
    CyclicBarrier barrier = new CyclicBarrier(taskCount, new Runnable()
    {
      private int count = 1;

      @Override
      public void run()
      {
        if (count <= ROUND)
        {
          System.out.println("==> Starte in die Runde " + count++);
        }
      }
    });
    Athlet a1 = new Athlet("Carl Lewis", barrier);
    Athlet a2 = new Athlet("Maurice Greene", barrier);
    Athlet a3 = new Athlet("Usain Bolt", barrier);
    executor.execute(a1);
    executor.execute(a2);
    executor.execute(a3);
    executor.shutdown();
  }
}
