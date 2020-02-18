package kap10_Exchanger_BlockingQueue.blockingqueue;

/**
 * Das Hauptprogramm für das Erzeuger-Verbraucher-Beispiel
 */
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import kap10_Exchanger_BlockingQueue.exchanger.Util;

public class MainDriver
{

  public static void main(String[] args) throws Exception
  {
    final String END_TOKEN = "_STOPP";

    // Pool für die beiden Threads
    ExecutorService executor = Executors.newFixedThreadPool(2);
    // Kommunikation-Queue
    BlockingQueue<String> queue = new ArrayBlockingQueue<String>(
        1000);

    // Erzeugen und Starten der Threads
    RandomStringProducer producer = new RandomStringProducer(queue,
        END_TOKEN);
    executor.submit(producer);
    
    RandomStringConsumer consumer = new RandomStringConsumer(queue,
        END_TOKEN);
    
    Future<int[]> result = executor.submit(consumer);

    // Ergebnis holen
    int[] frequency = result.get();

    executor.shutdown();

    for (int i = 0; i < frequency.length; i++)
    {
      System.out
          .println(Util.BUCHSTABEN.charAt(i) + " : " + frequency[i]);
    }

  }

}
