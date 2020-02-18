package kap18_Dining_Philosophers.Koordinator;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;


public class PhilosopherWithCoordinator implements Runnable
{ 
  private final String name;
  private final int philoNum;
  private final ForkCoordinator monitor;
  

  public PhilosopherWithCoordinator(String name, int philoNum, ForkCoordinator monitor )
  {
    super();
    this.name      = name;
    this.philoNum  = philoNum;
    this.monitor   = monitor;
  }
  
  @Override
  public void run()
  {
    while( true )
    {
      think();
      this.monitor.takeFork( this.philoNum );
      try
      {  
        eat();
      }
      finally
      {
        this.monitor.releaseFork( this.philoNum );
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
