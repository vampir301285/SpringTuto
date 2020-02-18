package kap08_LockObjekte_Semaphore;

/**
 * Codebeispiel für Thread-sicheren Modulo-Zähler mit einem Lock
 */
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ModuloCounter
{
  private final Lock lock = new ReentrantLock();
  private int count = 0;
  private final int mod;

  public ModuloCounter(int mod)
  {
    this.mod = mod;
  }

  public void increment()
  {
    lock.lock();
    try
    {
      count = (count + 1) % mod;
    } finally
    {
      lock.unlock();
    }
  }

  public void decrement()
  {
    lock.lock();
    try
    {
      count = (count - 1 + mod) % mod;
    } finally
    {
      lock.unlock();
    }
  }

  public int getValue()
  {
    lock.lock();
    try
    {
      return count;
    } finally
    {
      lock.unlock();
    }
  }
}
