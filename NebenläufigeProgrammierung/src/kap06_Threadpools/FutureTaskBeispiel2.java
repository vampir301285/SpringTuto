package kap06_Threadpools;

/**
 * Codebeispiel für einen abgeleiteten FutureTask
 */
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.function.Consumer;

class MyFutureTask<V> extends FutureTask<V>
{
  private Consumer<V> consumer;

  public MyFutureTask(Callable<V> callable, Consumer<V> consumer)
  {
    super(callable);
    this.consumer = consumer;
  }

  public MyFutureTask(Runnable runnable, V result,
      Consumer<V> consumer)
  {
    super(runnable, result);
    this.consumer = consumer;
  }

  @Override
  protected void done()
  {
    super.done();
    try
    {
      System.out
          .println("Thread " + Thread.currentThread().getName());
      V result = this.get();
      consumer.accept(result);
    } catch (InterruptedException | ExecutionException e)
    {
      e.printStackTrace();
      this.setException(e);
    }

  }
}

public class FutureTaskBeispiel2
{

  public static void main(String[] args) throws Exception
  {
    Callable<String> callable = () ->
    {
      return "Hallo";
    };

    FutureTask<String> futureTask = new MyFutureTask<String>(callable,
        System.out::println);

    new Thread(futureTask).start();
  }

}
