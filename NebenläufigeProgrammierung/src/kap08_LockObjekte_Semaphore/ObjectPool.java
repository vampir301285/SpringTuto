package kap08_LockObjekte_Semaphore;

/**
 * Codebeispiel für eine Realisierung eines Objektpools 
 * mit einem Semaphore-Objekt
 */
import java.util.LinkedList;
import java.util.concurrent.Semaphore;

class Point
{
}

public class ObjectPool
{
  private final LinkedList<Point> pool;
  private final Semaphore sem;

  public ObjectPool(int cap)
  {
    this.pool = new LinkedList<Point>();
    this.sem = new Semaphore(cap, true);
    for (int i = 0; i < cap; i++)
    {
      pool.add(new Point());
    }
  }

  public Point get() throws InterruptedException
  {
    sem.acquire();
    Point elem = null;
    synchronized (pool)
    {
      elem = pool.poll();
    }
    return elem;
  }

  public void release(Point p)
  {
    if (p == null)
      return;
    synchronized (pool)
    {
      pool.add(p);
    }
    sem.release();
  }
}
