package kap06_Threadpools;

/**
 * Codebeispiel für Exceptionsbehandlung durch den 
 * Default-Handler. 
 */
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ExceptionBeispiel2
{

  public static void main(String[] args)
  {
    ExecutorService executor = Executors.newCachedThreadPool();
    executor.execute(() -> System.out.println(1 / 0));
    executor.shutdown();
  }

}
