package kap06_Threadpools;

/**
 * Codebeispiel für die Verwendung der eigenen ThreadFactory 
 */
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

public class ThreadFactoryBeispiel
{

  public static void main(String[] args) throws InterruptedException
  {
    ExecutorService executor = Executors
        .newCachedThreadPool(new ThreadFactory()
        {
          @Override
          public Thread newThread(Runnable r)
          {
            Thread th = new Thread(r, "MyFactoryThread");
            th.setDaemon(true);
            return th;
          }
        });

    executor.execute(() -> System.out.println("Hallo"));

    TimeUnit.SECONDS.sleep(5);

  }

}
