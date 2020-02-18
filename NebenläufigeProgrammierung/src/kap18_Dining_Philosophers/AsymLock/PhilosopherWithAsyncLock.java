package kap18_Dining_Philosophers.AsymLock;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

import kap18_Dining_Philosophers.Fork;

public class PhilosopherWithAsyncLock implements Runnable
{
  private final String name;
  private final int philoNum;
  private final Fork left;
  private final Fork right;

  public PhilosopherWithAsyncLock(String name, int philoNum, Fork left, Fork right)
  {
    super();
    this.name = name;
    this.philoNum = philoNum;
    this.left = left;
    this.right = right;
  }

  @Override
  public void run()
  {
    while (true)
    {
      think();

      if (this.philoNum % 2 == 0)
      {
        this.right.lock();
        this.left.lock();
      }
      else
      {
        this.left.lock();
        this.right.lock();
      }
      
      try
      {
        eat();
      }
      finally
      {
        right.unlock();
        left.unlock();
      }
    }
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
