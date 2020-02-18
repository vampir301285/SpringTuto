package kap10_Exchanger_BlockingQueue.exchanger;

/**
 * Das Hauptprogramm für das Exchanger-Beispiel.
 */
import java.util.concurrent.Exchanger;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class MainDriver
{

  public static void main(String[] args) throws Exception
  {
    ExecutorService executor = Executors.newFixedThreadPool(2);

    Exchanger<String[]> exchanger = new Exchanger<String[]>();

    RandomStringProducer producer = new RandomStringProducer(
        exchanger);
    executor.submit(producer);

    RandomStringConsumer consumer = new RandomStringConsumer(
        exchanger);
    Future<int[]> result = executor.submit(consumer);

    int[] frequency = result.get();

    for (int i = 0; i < frequency.length; i++)
    {
      System.out
          .println(Util.BUCHSTABEN.charAt(i) + " : " + frequency[i]);
    }

    executor.shutdown();

  }

}
