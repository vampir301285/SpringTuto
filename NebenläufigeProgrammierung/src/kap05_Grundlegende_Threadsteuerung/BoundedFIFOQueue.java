package kap05_Grundlegende_Threadsteuerung;

/**
 * Codebeispiel für einen Ringpuffer zum Datenaustausch von Erzeugern und
 * Verbrauchern
 */

public class BoundedFIFOQueue<T>
{
  private final Object[] data;
  private int head;
  private int tail;
  private int count;

  public BoundedFIFOQueue(int cap)
  {
    data = new Object[cap];
    head = 0;
    tail = 0;
    count = 0;
  }

  public synchronized void put(T elem) throws InterruptedException
  {
    while (count == data.length)
    {
      wait();
    }
    count++;
    data[tail] = elem;
    tail = (tail + 1) % data.length;
    if (count == 1)
    {
      notifyAll();
    }
  }

  public synchronized T get() throws InterruptedException
  {
    while (count == 0)
    {
      wait();
    }
    count--;
    @SuppressWarnings("unchecked")
    T obj = (T) data[head];
    data[head] = null;
    head = (head + 1) % data.length;
    if (count == data.length - 1)
    {
      notifyAll();
    }
    return obj;
  }
}
