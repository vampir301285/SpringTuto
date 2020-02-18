package kap18_Dining_Philosophers.Semaphor;

import java.util.concurrent.Semaphore;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

import kap18_Dining_Philosophers.Fork;

public class PhilosopherWithSemaphore implements Runnable
{
  private final String name;
  private final Fork left;
  private final Fork right;
  private final Semaphore semaphore;

  public PhilosopherWithSemaphore(String name, Fork left, Fork right, Semaphore semaphore)
  {
    super();
    this.name = name;
    this.left = left;
    this.right = right;
    this.semaphore = semaphore;
  }

  @Override
  public void run()
  {
    while (true)
    {
      think();
      try
      {
        semaphore.acquire();
      }
      catch (InterruptedException exce)
      {
        break;
      }

      left.lock();
      right.lock();

      try
      {
        eat();
      }
      finally
      {
        right.unlock();
        left.unlock();
        semaphore.release();
      }
    }

    return;
  }

  private void think()
  {
    try
    {
      System.out.println(name + " is thinking");
      TimeUnit.MILLISECONDS.sleep(100 + ThreadLocalRandom.current().nextInt(1000));
    }
    catch (InterruptedException e)
    {

    }
  }

  private void eat()
  {
    try
    {
      System.out.println(name + " is eating");
      TimeUnit.MILLISECONDS.sleep(100 + ThreadLocalRandom.current().nextInt(1000));
    }
    catch (InterruptedException e)
    {

    }
  }

}
