package kap06_Threadpools;

/**
 * Codebeispiel für eine einfache Verwendung von Executor
 */
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class EinfachesExecutorBeispiel
{
  public static void main(String[] args)
  {
    ExecutorService executor = Executors.newFixedThreadPool(1);
    executor.execute(() -> System.out.println("Hallo Welt"));

    executor.shutdown();
  }

}
