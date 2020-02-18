package kap06_Threadpools;

/**
 * Codebeispiel für einen durch Ableitung angepassten Executor
 */
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

public class MonitoredExecutor extends ThreadPoolExecutor
{
  private final ThreadLocal<Long> startTime = new ThreadLocal<>();
  private final AtomicLong counter = new AtomicLong();

  public MonitoredExecutor()
  {
    super(3, Runtime.getRuntime().availableProcessors(), 30,
        TimeUnit.SECONDS, new ArrayBlockingQueue<>(10),
        new ThreadPoolExecutor.AbortPolicy());
  }

  @Override
  protected void beforeExecute(Thread t, Runnable r)
  {
    counter.incrementAndGet();
    startTime.set(System.nanoTime());
    super.beforeExecute(t, r);
  }

  @Override
  protected void afterExecute(Runnable r, Throwable t)
  {
    super.afterExecute(r, t);
    System.out
        .println("Dauer : " + (System.nanoTime() - startTime.get())
            + "(Thread : " + Thread.currentThread() + ")");
  }

  @Override
  protected void terminated()
  {
    super.terminated();
    System.out.println("Anzahl Tasks " + counter.get());
  }

  public static void main(String[] args)
  {
    Runnable task = new Runnable()
    {
      @Override
      public void run()
      {
        System.err
            .println("Thread : " + Thread.currentThread().getId());

        double a = 2;
        for (int i = 0; i < 100000; i++)
        {
          a += Math.log(Math.sqrt(a)) * Math.log(Math.sqrt(a));
        }
        System.err.println("Thread : " + a);
        // try
        // {
        // TimeUnit.MILLISECONDS.sleep(1);
        // }
        // catch (InterruptedException e)
        // {
        // e.printStackTrace();
        // }
      }
    };

    MonitoredExecutor executor = new MonitoredExecutor();
    for (int i = 0; i < 15; i++)
    {
      executor.execute(task);
    }
    executor.shutdown();
  }

}
