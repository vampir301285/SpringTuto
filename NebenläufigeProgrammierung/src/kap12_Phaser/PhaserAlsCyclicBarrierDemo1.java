package kap12_Phaser;

/**
 * Codebeispiel: Phaser als CyclicBarrier. Die Rundenzahl wird über die Phase gesteuert, wobei der
 * Phaser selbst die Terminierung übernimmt
 */

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Phaser;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

public class PhaserAlsCyclicBarrierDemo1
{
  private static class Task implements Runnable
  {
    private final Phaser phaser;
    private final String name;

    private Task(String name, Phaser phaser)
    {
      super();
      this.name = name;
      this.phaser = phaser;
    }

    @Override
    public void run()
    {
      while (true)
      {
        this.phaser.arriveAndAwaitAdvance();
        if( phaser.isTerminated() )
          break;
        System.out.println(this.name + " startet in Runde " + this.phaser.getPhase());
        randomDelay(1000);
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

  private static final int ROUND = 3;
  
  public static void main(String[] args) throws InterruptedException
  {
    ExecutorService executor = Executors.newFixedThreadPool(3);

    Phaser phaser = new Phaser(3){
      @Override
      protected boolean onAdvance(int phase, int registeredParties)
      {
        if( phase >= ROUND )
          return true;
        else
          return false;
      }
      
    };
    Task a1 = new Task("Task 1", phaser);
    Task a2 = new Task("Task 2", phaser);
    Task a3 = new Task("Task 3", phaser);

    executor.execute(a1);
    executor.execute(a2);
    executor.execute(a3);

    executor.shutdown();
  }
}
