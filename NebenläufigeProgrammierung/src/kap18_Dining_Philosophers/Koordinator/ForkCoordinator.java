package kap18_Dining_Philosophers.Koordinator;

import java.util.Arrays;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ForkCoordinator
{
  private final int N;
  private int[] forks;
  private Lock lock;
  private Condition[] okToEat;

  public ForkCoordinator(int numPhilo)
  {
    this.N = numPhilo;
    this.forks = new int[N];
    Arrays.fill(this.forks, 2);
    this.lock = new ReentrantLock();
    this.okToEat = new Condition[N];
    for (int i = 0; i < N; i++)
      this.okToEat[i] = this.lock.newCondition();
  }

  public void takeFork(int i)
  {
    this.lock.lock();
    try
    {
      while (this.forks[i] != 2)
        this.okToEat[i].awaitUninterruptibly();

      this.forks[(i + 1) % N] -= 1;
      this.forks[(i - 1 + N) % N] -= 1;
    }
    finally
    {
      this.lock.unlock();
    }
  }

  public void releaseFork(int i)
  {
    this.lock.lock();
    try
    {
      this.forks[(i + 1) % N] += 1;
      this.forks[(i - 1 + N) % N] += 1;

      if (this.forks[(i + 1) % N] == 2)
        this.okToEat[(i + 1) % N].signalAll();
      if (this.forks[(i - 1 + N) % N] == 2)
        this.okToEat[(i - 1 + N) % N].signalAll();
    }
    finally
    {
      this.lock.unlock();
    }
  }
}
