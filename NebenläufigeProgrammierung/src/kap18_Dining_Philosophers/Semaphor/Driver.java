package kap18_Dining_Philosophers.Semaphor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

import kap18_Dining_Philosophers.Fork;



public class Driver
{

  public static void main(String[] args) throws Exception
  {
    final int N = 7;

    Semaphore semaphore = new Semaphore(N - 1);

    Fork[] forks = new Fork[N];
    for (int i = 0; i < N; i++)
    {
      forks[i] = new Fork();
    }

    ExecutorService threadPool = Executors.newFixedThreadPool(N);
    PhilosopherWithSemaphore[] philosophers = new PhilosopherWithSemaphore[N];
    for (int i = 0; i < N; i++)
    {
      philosophers[i] = new PhilosopherWithSemaphore("Philo "+i, forks[i], forks[(i + 1) % N], semaphore);
      threadPool.submit(philosophers[i]);
    }

    threadPool.shutdown();
  }

}
