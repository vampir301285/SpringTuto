package kap08_LockObjekte_Semaphore;

/**
 * Codebeispiel für einen beschränkten Puffer mit Lock
 *  und Condition
 */
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class BoundedFIFOQueueWithLock<T>
{
  private final Object[] data;
  private int head;
  private int tail;
  private int count;

  private final Lock lock = new ReentrantLock();
  private final Condition notFull = lock.newCondition();
  private final Condition notEmpty = lock.newCondition();

  public BoundedFIFOQueueWithLock(int cap)
  {
    data = new Object[cap];
    head = 0;
    tail = 0;
    count = 0;
  }

  public void put(T elem) throws InterruptedException
  {
    lock.lock();
    try
    {
      while (count == data.length)
      {
        notFull.await();
      }

      count++;
      data[tail] = elem;
      tail = (tail + 1) % data.length;

      if (count == 1)
      {
        notEmpty.signalAll();
      }
    } finally
    {
      lock.unlock();
    }
  }

  @SuppressWarnings("unchecked")
  public T get() throws InterruptedException
  {
    lock.lock();
    try
    {
      while (count == 0)
      {
        notEmpty.await();
      }

      count--;
      T obj = (T) data[head];
      data[head] = null;
      head = (head + 1) % data.length;

      if (count == data.length - 1)
      {
        notFull.notifyAll();
      }
      return obj;
    } finally
    {
      lock.unlock();
    }
  }
}
