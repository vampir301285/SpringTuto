package kap18_Dining_Philosophers.Koordinator;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class Driver
{
  public static void main(String[] args) throws Exception
  {
    final int N = 7;
    
    ForkCoordinator monitor = new ForkCoordinator(N);

    ExecutorService threadPool = Executors.newFixedThreadPool(N);
    PhilosopherWithCoordinator[] philosophers = new PhilosopherWithCoordinator[N];
    for (int i = 0; i < N; i++)
    {
      philosophers[i] = new PhilosopherWithCoordinator("Philo "+i, i, monitor );
      threadPool.submit(philosophers[i]);
    }

    threadPool.shutdown();
  }

}
