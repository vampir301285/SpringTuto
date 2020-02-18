package kap06_Threadpools;
/**
 * Codebeispiel für einfache Verwendung von ExecutorService
 * und Future
 */

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class EinfachesFutureBeispiel
{
  public static void main(String[] args)
  {
    Callable<Integer> callable = new Callable<Integer>()
    {
      @Override
      public Integer call() throws Exception
      {
        // Implementierung des Tasks
        return 42;
      }
    };

    ExecutorService executor = Executors.newCachedThreadPool();
    Future<Integer> future = executor.submit(callable);

    try
    {
      Integer result = future.get();
      System.out.println(result);
    } catch (InterruptedException | ExecutionException e)
    {
      // ...
    }
  }

}
