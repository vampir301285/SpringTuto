package kap18_Dining_Philosophers.AsymWaitRelease;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

import kap18_Dining_Philosophers.Fork;



public class PhilosopherWithWaitRelease implements Runnable
{  
  private final String name;
  private final int philoNum;
  private final Fork left;
  private final Fork right;

  public PhilosopherWithWaitRelease(String name, int philoNum, Fork left, Fork right)
  {
    super();
    this.name      = name;
    this.philoNum  = philoNum;
    this.left      = left;
    this.right     = right;
  }
  
  @Override
  public void run()
  {
    while( true )
    {
      think();   
      if( philoNum%2 == 0 ) 
      {
        right.lock();
        try
        {
          if( left.tryLock() )
          {
            try
            {
              eat();
            }
            finally
            {
              left.unlock();
            }
          }
        }
        finally
        {
          right.unlock();
        }
      }
      else
      {
        left.lock();
        try
        {
          if( right.tryLock() )
          {
            try
            {
              eat();
            }
            finally
            {
              right.unlock();
            }
          }
        }
        finally
        {
          left.unlock();
        }
      }
    }
  }
  
  private void think()
  {
    try
    {
      System.out.println( name + " is thinking");
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
      System.out.println( name + " is eating");
      TimeUnit.MILLISECONDS.sleep(100 + ThreadLocalRandom.current().nextInt(1000));
    }
    catch (InterruptedException e)
    {

    }
  }
}
