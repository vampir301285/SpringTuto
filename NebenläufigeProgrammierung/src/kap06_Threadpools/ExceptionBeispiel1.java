package kap06_Threadpools;

/**
 * Codebeispiel für Exceptionsbehandlung durch den 
 * Default-Handler. Exception wird durch get geworfen.
 */
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ExceptionBeispiel1
{

  public static void main(String[] args)
  {
    ExecutorService executor = Executors.newCachedThreadPool();
    Future<?> future = executor
        .submit(() -> System.out.println(1 / 0));

    try
    {
      future.get();
    } catch (InterruptedException | ExecutionException e)
    {
      e.printStackTrace();
    }

    executor.shutdown();
  }

}
