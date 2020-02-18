package kap06_Threadpools;

/**
 * Codebeispiel für Exceptionsbehandlung durch den 
 * Default-Handler. In diesem Fall erscheint keine 
 * Ausgabe auf der Konsole. Der Grund ist, dass bei der
 *  Verwendung von submit jede nicht behandelte Ausnahme 
 *  von Runnable oder Callable abgefangen wird 
 */
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ExceptionBeispiel3
{

  public static void main(String[] args)
  {
    ExecutorService executor = Executors.newCachedThreadPool();
    executor.submit(() -> System.out.println(1 / 0));
    executor.shutdown();
  }

}
