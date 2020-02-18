package kap06_Threadpools;

/**
 * Codebeispiel für FutureTask
 */
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

public class FutureTaskBeispiel1
{

  public static void main(String[] args) throws Exception
  {
    Callable<String> callable = () ->
    {
      return "Hallo";
    };

    FutureTask<String> futureTask = new FutureTask<String>(callable);

    new Thread(futureTask).start();

    System.out.println("Ergebnis: " + futureTask.get());
  }

}
