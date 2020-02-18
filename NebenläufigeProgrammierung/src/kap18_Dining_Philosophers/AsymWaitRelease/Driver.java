package kap18_Dining_Philosophers.AsymWaitRelease;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import kap18_Dining_Philosophers.Fork;



public class Driver
{
  public static void main(String[] args) throws Exception
  {
    final int N = 7;

    Fork[] forks = new Fork[N];
    for (int i = 0; i < N; i++)
    {
      forks[i] = new Fork();
    }

    ExecutorService threadPool = Executors.newFixedThreadPool(N);
    PhilosopherWithWaitRelease[] philosophers = new PhilosopherWithWaitRelease[N];
    for (int i = 0; i < N; i++)
    {
      philosophers[i] = new PhilosopherWithWaitRelease("Philo "+i, i, forks[i], forks[(i + 1) % N]);
      threadPool.submit(philosophers[i]);
    }

    threadPool.shutdown();
  }

}
